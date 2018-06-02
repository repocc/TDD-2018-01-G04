(ns api-tickets
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
            [ring.middleware.cors :refer [wrap-cors]]
            ))

(use 'db.db-client)
(use 'db.seeds)
(use 'controllers.user-controller)
(use 'controllers.role-controller)
(use 'controllers.tickettype-controller)
(use 'controllers.project-controller)

(defroutes app-routes

  (POST "/api/auth" request
    (auth-by-username request))

  (GET "/api/user" [] 
    {:status 200 :body (find-all-users)}
  )

  (GET "/api/role" [] 
    {:status 200 :body (find-all-roles)}
  )

  (GET "/api/tickettype" [] 
    {:status 200 :body (find-all-ticket-types)}
  )

  (POST "/api/project" request
    (store-project request))

  (GET "/api/project" {params :query-params}
    {:status 200 :body (find-projects-by-username params)}
  )


  (route/resources "/")

  (route/not-found "Not Found"))

(defn wrap-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        (println e)
        {:status 500 :body "Internal server error"}))))

(def app
  (-> (handler/api app-routes)
      (wrap-cors routes #".*")
      (wrap-cors routes identity)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)
      (wrap-exception-handling)
  )
)

(defn -main [& args]
  (jetty/run-jetty app)
)