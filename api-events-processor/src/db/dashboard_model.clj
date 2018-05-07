(ns db.dashboard-model)
(use 'db.base-model)

(defn db-find-all-dashboards [] (db-find-all :dashboards))

(defn db-count-all-dashboards [] (db-count-all :dashboards))

(defn db-get-dashboard-by-id [id] (db-find-where :dashboards :id id))

(defn db-drop-dashboard-by-id [id] (db-drop-where :dashboards :id id))

(defn db-store-dashboard [dashboard] (db-store :dashboards dashboard))
