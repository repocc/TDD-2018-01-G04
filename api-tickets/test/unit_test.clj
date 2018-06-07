(ns unit-test
  (:use config)
  (:require [clojure.test :refer :all]
            [controllers.user-controller :refer :all]
            [controllers.role-controller :refer :all]
            [controllers.tickettype-controller :refer :all]
            [controllers.project-controller :refer :all]
            [controllers.ticket-controller :refer :all]
            [controllers.comment-controller :refer :all]
            ))

(use-fixtures :once wrap-setup)

(def ticket {:title "test" :description "description" :type "type" :assigned "user-test@gmail.com" :project "1" :state "open"} )
(def ticket-state {:id "2", :state "in-progress"})
(def comment-test {:ticket "2", :author "user-test@gmail.com", :text "Comment in ticket"})

(deftest login-test
  (testing "login test with correct user"
    (let [
        response (auth-by-username {:params {:username "user-test@gmail.com"}})
    ]
    (is ( = 
          response
          {:status 200 :body {:username "user-test@gmail.com"}}
        ) 
    )))
  (testing "login test with incorrect user"
    (let [
        response (auth-by-username {:params {:username "non-existent-user-test@gmail.com"}})
    ]
    (is ( = 
          response
          {:status 500 }
        ) 
    ))))

(deftest list-user-test
  (testing "verify users exist")
  (let [
      response (find-all-users)
    ]
    (is ( =
          response
          [{:username "user-test@gmail.com"}]
        ))))

(deftest list-role-test
  (testing "verify role exist")
  (let [
      response (find-all-roles)
    ]
    (is ( =
          response
          [{:id "role-test"}]
        ))))

(deftest list-ticket-type-test
  (testing "verify ticket types exist")
  (let [
      response (find-all-ticket-types)
    ]
    (is ( =
          response
          [{:id "0" :name "ticket-type-test"}]
        ))))

(deftest get-project-by-id-test
  (testing "Get name project"
  (let [
        response (find-project-by-id "1")
    ]
    (is ( =
          (get-in response [:body :name])
          "project-test"
        )))))

(deftest create-ticket-in-project-test
  (testing "Create ticket in existing project and store in project"
  ( let [
        ok (store-ticket {:params ticket})
        response (find-project-by-id "1")
    ]
    (is ( =
          (:title (first(get-in response [:body :tickets])))
          "test"
      )))))

(deftest change-state-ticket-test
  (testing "Change state for ticket"
    (let [
      sendTicket (update-ticket-by-id {:params ticket-state})
      response (find-ticket-by-id "2")
    ]
    (is ( =
          (get-in response [:body :state])
          "in-progress"
    )))))

(deftest insert-comment-in-ticket-test
  (testing "insert comment in ticket"
    (let [
      ok (store-comment {:params comment-test})
      response (find-ticket-by-id "2")
    ]
    (is ( =
          (first (get-in response [:body :comments ]))
          comment-test
    )))))
