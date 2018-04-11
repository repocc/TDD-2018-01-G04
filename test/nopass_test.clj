(ns nopass-test
  (:require [clojure.test :refer :all]
            [data-processor :refer :all])
  			(:use clojure.pprint))

(def rules '((define-counter "email-count" []
               (and true true))
             (define-counter "spam-count" []
               (and (current "spam") true))
             (define-counter "resent-by" [(current "sender")]
				(includes? (current "meeting") "meeting")
			 )
            )
)


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

(deftest complej-condition-counter-test
  (let [st0 (initialize-processor rules)
        st1 (process-data-dropping-signals st0 {"sender" "Pedro","receiver" "Sofia","subject" "meeting"})
        st2 (process-data-dropping-signals st1 {"sender" "Sofia","receiver" "Pedro","subject" "meeting"})
        st3 (process-data-dropping-signals st2 {"sender" "Pedro","receiver" "Sofia","subject" "meeting"})
        ]
        (pprint st3)
    (is (= 1 (query-counter st3 "resent-by" ["Sofia"])))
    (is (= 2 (query-counter st3 "resent-by" ["Pedro"])))))