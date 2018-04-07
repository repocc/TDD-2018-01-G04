(ns rules-evaluator)
(use 'expression)
(use 'counter-processor)
(use 'signal-processor)


(defn evaluate-counters-rules [counters data new-data]
	(map #(process-counter % data new-data) counters))

(defn evaluate-signal-rules [signals data new-data counters]
	(remove #(= % ())(map #(process-signal % data new-data counters) signals)))


(defn evaluate-rules [state new-data]
	(let [
		signal-result (evaluate-signal-rules (state :signals) (state :data) new-data (state :counters))
		counters-rules (evaluate-counters-rules (state :counters) (state :data) new-data)
	]
	[(merge state {:counters counters-rules}) signal-result]))