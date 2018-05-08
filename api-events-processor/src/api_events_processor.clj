(ns api-events-processor
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
            [ring.middleware.cors :refer [wrap-cors]]
            ))

;;(use 'utils.string-util)
(use 'db.db-client)
(use 'db.seeds)
(use 'controllers.rule-controller)
(use 'controllers.event-controller)
(use 'controllers.user-controller)
(use 'controllers.dashboard-controller)
(use 'controllers.chrono-controller)

(def frequency 10)

(defroutes app-routes
  (GET "/api/rule" {params :query-params}
    {:status 200 :body (find-all-rules params)}
  )

  (GET "/api/rule/count" [] 
    {:status 200 :body (count-all-rules)}
  )

  (POST "/api/dashboard" request
    (store-dashboard request))
  
  (GET "/api/dashboard" [] 
    {:status 200 :body (find-all-dashboards)}
  )

  (GET "/api/dashboard/count" [] 
    {:status 200 :body (count-all-dashboards)})
  
  (GET "/api/counter" [] 
    {:status 200 :body (find-all-counters)}
  )

  (GET "/api/dashboard/:id" [id] 
    (get-dashboard-by-id id)
  )
  
  (GET "/api/snapshot" [] 
    {:status 200 :body (find-snapshot)}
  )

  (DELETE "/api/dashboard/:id" [id] 
    {:status 200 :body (drop-dashboard-by-id id)}
  )

  (POST "/api/rule" request
    (store-rule request))
  
  (POST "/api/auth" request
    (auth-by-username request))

  (POST "/api/event" request
    (process-events request))

  (route/resources "/")

  (route/not-found "Not Found"))


(def app
  (-> (handler/api app-routes)
      ;; accept everything
      (wrap-cors routes #".*")
      (wrap-cors routes identity)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)
  )
)

(run-chrono frequency)

(defn -main [& args]
  (jetty/run-jetty app)
)