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

(defroutes app-routes
  (GET "/api/rule" [] 
    {:status 200 :body (find-all-rules)}
  )

  (GET "/api/rule/count" [] 
    {:status 200 :body (count-all-rules)}
  )

  (GET "/api/counter" [] 
    {:status 200 :body (find-all-counters)}
  )

  (OPTIONS "/api/rule" [] 
    {:status 200 }
  )

  (DELETE "/api/rule/:name" [name] 
    (drop-rule-by-name name)
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

(defn -main [& args]
  (jetty/run-jetty app)
)