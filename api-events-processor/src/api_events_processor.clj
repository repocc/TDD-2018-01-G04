(ns api-events-processor
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
            ))

;;(use 'utils.string-util)
(use 'db.db-client)
(use 'controllers.rule-controller)

(defroutes app-routes
  (GET "/api/rule" [] 
    {:status 200 :body (find-all-rules)}
  )

  (DELETE "/api/rule/:name" [name] 
    (drop-rule-by-name name)
  )

  (POST "/api/rule" request
    (store-rule request))

  (route/resources "/")

  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)
  )
)

(defn -main [& args]
  (jetty/run-jetty app)
)