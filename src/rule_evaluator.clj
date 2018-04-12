(ns rule-evaluator)
(use 'condition)
(use 'counter-processor)
(use 'signal-processor)

(defn evaluate-counters [counters data new-data]
	(map #(process-counter % data new-data) counters))

(defn evaluate-counter-steps [counter-steps data new-data]
	(map #(process-counter % data new-data) counter-steps))

(defn evaluate-signals [signals counters data new-data]
	(remove #(= % ()) (map #(process-signal % data new-data counters) signals)))


(defn evaluate-rules [state new-data]
	(let [
		signal-results (evaluate-signals (state :signals) (state :counters) (state :data) new-data)
		counter-results (evaluate-counters (state :counters) (state :data) new-data)
		counter-step-results (evaluate-counter-steps (state :counters-steps) (state :data) new-data)
	]
	[(merge state {:counters counter-results}) signal-results]))