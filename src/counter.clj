(ns counter)

(defn get-counter-by-name [counters counter-name] 
	(first (filter #(= (:name %) counter-name) counters))) 

(defn evaluate-rules [state] ;;new-data]
	(let [
		;;conditions (map evaluate-condition (:counters state))
	]
	state))