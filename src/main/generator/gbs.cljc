(ns generator.gbs
  (:require [datascript.core :as d]
            ;;[ecological.generator.gbs.moves :as moves]
            ;;[ecological.generator.gbs.export :as export]
            ;;[ecological.generator.gbs.assets :as assets]
            ))

(def genboy-schema
  {:game-entity/uuid {:db/cardinality :db.cardinality/one :db/unique :db.unique/identity}
   :game-entity/type {:db/cardinality :db.cardinality/one}
   })

(def db-conn (d/create-conn genboy-schema))

(def design-moves
  [
   ])


(def initial-transaction
  [;;load-resources
   ;;load-gbs-projects
   ]
  )


(def records
  {:db-conn db-conn
   :db-schema genboy-schema
   ;; :design-moves design-moves
   ;; :exporter #(export/export-gbs-project gbs-basic db-conn)
   ;; :export-most-recent #(export/export-most-recent-artifact gbs-basic db-conn)
   ;; :initial-transaction [load-resources load-gbs-projects]
   ;; :setup [(assets/load-manifest) (assets/load-scene-sources)]
   ;; :export-project-view #(export/export-project-view db-conn)
   })
