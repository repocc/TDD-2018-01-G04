(ns negative-test
  (:require [clojure.test :refer :all]
            [data-processor :refer :all]))


(def rules '((define-counter "email-important-spam-count" [(current "spam")]
               (current "important"))
             (define-counter "email-count" []
               true)
             (define-counter "spam-count" []
               (current "spam"))
             (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])
                                                (counter-value "email-count" []))}
               true)
             (define-signal {"spam-fraction-error" (/ (counter-value "spam-count" [true])
                                                (counter-value "email-count" []))}
               true)
             (define-counter "spam-important-table" [(current "spam")
                                                     (current "important")]
               true)))


(defn process-data-dropping-signals [state new-data]
  (first (process-data state new-data)))

(deftest failures-counter-test
  (testing "Count incoming data by current condition"
    (testing "when fields are missing"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true})
            st2 (process-data-dropping-signals st1 {"spam" true})]
        (is (= 0 (query-counter st2 "spam-important-table" [true true])))))
    (testing "when counter-args are missing"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true, "important" true})
            st2 (process-data-dropping-signals st1 {"spam" true, "important" false})]
        (is (= 0 (query-counter st2 "spam-important-table" [true])))))
    (testing "when condition is true but param no exist in current data"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"important" true})
            st2 (process-data-dropping-signals st1 {"important" true})]
        (is (= 0 (query-counter st2 "email-important-spam-count" [true])))
        (is (= 0 (query-counter st2 "email-important-spam-count" [false])))
        (is (= 0 (query-counter st2 "email-important-spam-count" [])))))
    ))

(deftest failures-query-test
  (testing "Count incoming data by current condition"
    (testing "when counter name no exist"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true, "important" true})
            st2 (process-data-dropping-signals st1 {"spam" true, "important" true})]
        (is (= 0 (query-counter st2 "spam-important" [true true])))))
    (testing "when there are more fields"
      (let [st0 (initialize-processor rules)
            st1 (process-data-dropping-signals st0 {"spam" true, "important" true})
            st2 (process-data-dropping-signals st1 {"spam" true, "important" false})]
        (is (= 0 (query-counter st2 "spam-important-table" [true true false])))
        (is (= 0 (query-counter st2 "spam-important-table" [true false false])))))
    ))

(deftest signal-resul-error-test
  (testing "Verify the result of the signal"
    (testing "when the counters are incorrect"
      (let [st0 (initialize-processor rules)
        	[st1 sg1] (process-data st0 {"spam" true})
        	[st2 sg2] (process-data st1 {"spam" true})
        	[st2 sg3] (process-data st2 {"spam" false})]
        (is (= '({"spam-fraction" 1} {"spam-fraction-error" 0}) sg3))))
    ))