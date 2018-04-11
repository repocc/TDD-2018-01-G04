(ns nopass-test
  (:require [clojure.test :refer :all]
            [data-processor :refer :all]))

(def rules '((define-counter "email-count" []
               (and true true))
             (define-counter "spam-count" []
               (and (current "spam") (current "spam")))
             ))


(defn process-data-dropping-signals [state new-data]
  (first (process-data state new-data)))


(deftest unconditional-counter-test
  (let [st0 (initialize-processor rules)
        st1 (process-data-dropping-signals st0 {"spam" true})
        st2 (process-data-dropping-signals st1 {"spam" true})]
    (is (= 2
           (query-counter st2 "email-count" [])))))

(deftest conditional-counter-test
  (let [st0 (initialize-processor rules)
        st1 (process-data-dropping-signals st0 {"spam" true})
        st2 (process-data-dropping-signals st1 {"spam" true})]
    (is (= 2
           (query-counter st2 "spam-count" [])))))