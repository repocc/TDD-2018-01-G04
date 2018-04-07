(ns pass-test
  (:require [clojure.test :refer :all]
    [data-processor :refer :all])
  (:use clojure.pprint))

(def rules '((define-counter "email-count" []
               true)
             (define-counter "spam-count" []
               (current "spam"))
             (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])
                                                (counter-value "email-count" []))}
               true)
             (define-counter "spam-important-table" [(current "spam")
                                                     (current "important")]
               true)))


(defn process-data-dropping-signals [state new-data]
  (first (process-data state new-data)))

(deftest initial-state-test
  (testing "Query counter from initial state"
    (is (= 0
           (query-counter (initialize-processor rules) "spam" [])))))

(deftest unconditional-counter-test
  (let [st0 (initialize-processor rules)
        st1 (process-data-dropping-signals st0 {"spam" true})
        ;st2 (process-data-dropping-signals st1 {"spam" true})
        ]
        (pprint (query-counter (process-data-dropping-signals st0 {"spam" true}) "email-count" []))
    (is (= 1
           (query-counter st1 "email-count" [])))))
