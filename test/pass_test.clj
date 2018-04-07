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
        st2 (process-data-dropping-signals st1 {"spam" true})]
    (is (= 2
           (query-counter st2 "email-count" [])))))

(deftest conditional-counter-test
  (testing "Count incoming data by current condition"
    (testing "when repeated"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true})
            st2 (process-data-dropping-signals st1 {"spam" true})
            st3 (process-data-dropping-signals st2 {"spam" true})]
        (is (= 3
               (query-counter st3 "spam-count" [])))))
    (testing "when ignored field varies"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true, "noise" 1})
            st2 (process-data-dropping-signals st1 {"spam" true, "noise" 2})
            st3 (process-data-dropping-signals st2 {"spam" true, "noise" 3})]
        (is (= 3
               (query-counter st3 "spam-count" [])))))
    (testing "when considered field varies"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true})
            st2 (process-data-dropping-signals st1 {"spam" false})
            st3 (process-data-dropping-signals st2 {"spam" true})]
        (is (= 2
               (query-counter st3 "spam-count" [])))))))

(deftest contingency-table-counter-test
  (let [st0 (initialize-processor rules)
        st1 (process-data-dropping-signals st0 {"spam" true, "important" true})
        st2 (process-data-dropping-signals st1 {"spam" true, "important" false})
        st3 (process-data-dropping-signals st2 {"spam" true, "important" false})
        st4 (process-data-dropping-signals st3 {"spam" false, "important" true})
        st5 (process-data-dropping-signals st4 {"spam" false, "important" true})
        st6 (process-data-dropping-signals st5 {"spam" false, "important" true})
        st7 (process-data-dropping-signals st6 {"spam" false, "important" false})
        st8 (process-data-dropping-signals st7 {"spam" false, "important" false})
        st9 (process-data-dropping-signals st8 {"spam" false, "important" false})
        end-state (process-data-dropping-signals st9 {"spam" false, "important" false})]
    (is (= 1
           (query-counter end-state "spam-important-table" [true true])))
    (is (= 2
           (query-counter end-state "spam-important-table" [true false])))
    (is (= 3
           (query-counter end-state "spam-important-table" [false true])))
    (is (= 4
           (query-counter end-state "spam-important-table" [false false])))))
