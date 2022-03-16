(ns generator.library
  (:require [datascript.core :as d]
            ;[ecological.generator.image :as image]
            ;[ecological.generator.image.export]
            [generator.gbs   :as gbs]
            ))


(def database-records
  {:default-database gbs/records}
  )

(defonce current-database
  (atom {:db-conn nil
         :db-schema {}
         }))

(defn update-database [database-id]
  (let [new-database (get database-records database-id :default-database)]
    (if (and new-database (get new-database :db-conn false))
      (let [swap-in! (fn [db-id]
                       (swap! current-database assoc-in [db-id]
                              (get new-database db-id)))]
        (swap-in! :db-conn)
        (swap-in! :db-schema)
        )
      (println (str "Database not found: " database-id)))))


(defn setup-database [])
