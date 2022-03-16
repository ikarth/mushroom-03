(ns generator.gencore
  (:require [datascript.core :as d]
            [generator.randomness :as randomness]
            [generator.library :as library]
            ))

(defn random-from-range [low high]
  (let [[low high] (sort [low high])]
    (+  (* (randomness/randomness-rand) (- high low)) low)))

(defn timestamp []
  (#? (:cljs (js/parseInt (.now js/Date))
       :clj (inst-ms inst)
       )))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn log-db
  "Log a complete listing of the entities in the provided `db` to the console."
  [db]
  (d/q '[:find ?any ?obj :where [?obj :type ?any]] @db)) ;todo: log to console...


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn setup []
  (library/setup-database))

(defn switch-database [database-id]
  (library/update-database database-id))

(defn reset-the-database!
  [database-id]
  (switch-database database-id)
  (if-let [db-conn (get @library/current-database :db-conn)]
    (let [db-schema (get @library/current-database :db-schema)]
      (d/reset-conn! db-conn (d/empty-db db-schema))
      ;; TODO: initial transaction goes here...
      db-conn)))

(defn make-empty-project [database-id]
  (println (str "Switching to: " database-id))
  (assert (not (undefined? database-id)) "Tried to switch to an undefined generator database.")
  (switch-database database-id)
  (println "Resetting database...")
  (reset-the-database! database-id))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Fetch views

(defn fetch-internal-view
  "Returns the internal database. Intended mostly for debugging visualization."
  []
  (if-let
      [db-conn (get @library/current-database :db-conn)]
    (let []
         db-conn)))

(defn fetch-input-view
  "Returns the input that is being used as the starting point. "
  [])

(defn fetch-trace-view
  "Returns the trace of design moves that have been executed. "
  [])

(defn fetch-output-view
  "Returns an artifact generated by the current generator. "
  [])

(defn fetch-node-view
  "Returns the current generator node structure. "
  [])
