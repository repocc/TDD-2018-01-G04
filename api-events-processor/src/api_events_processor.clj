(ns api-events-processor
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [rage-db.core :as rdb]
            ))

;; Utility function to parse an integer
(defn parse-number
  "Reads a number from a string. Returns nil if not a number."
  [s]
  (if (re-find #"^-?\d+\.?\d*$" s)
    (read-string s)))

;; Creates a new in memory database
(def db (rdb/create "example"))

;; Finds all the cars from the database
(defn db-find-all-cars [] (rdb/keyspace db :cars))

;; Finds a car by id
(defn db-find-car-by-id [id] (rdb/where db :cars :id (parse-number id)))  

;; Stores a new car
(defn db-store-car [car] (rdb/insert db :cars car))

;; Store an initial set of cars to start with
(rdb/insert db :cars
  {:id 1
   :brand  "Honda"
   :model "Civic"
   :color "Black"})

(rdb/insert db :cars
  {:id 2
   :brand  "Honda"
   :model "Accord"
   :color "Red"})

(defroutes app-routes
  (GET "/example/api/car" [] 
    {:status 200 :body (db-find-all-cars)}
  )

  (GET "/example/api/car/:id" [id] 
    {:status 200 :body (db-find-car-by-id id)}
  )  

  (POST "/example/api/car" request
    (let [id (get-in request [:params :id])
          brand (get-in request [:params :brand])
          model (get-in request [:params :model])
          color (get-in request [:params :color])
          car {:id id :brand brand :model model :color color}]
      (db-store-car car)     
      {:status 201 :body car}
    )
  )

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