(ns generator.gbs
  (:require [datascript.core :as d]
            ;;[ecological.generator.gbs.moves :as moves]
            ;;[ecological.generator.gbs.export :as export]
            ;;[ecological.generator.gbs.assets :as assets]
            ))



;;;
;;; Generative Operations
;;;

;;; Generative operations are run at generation time. They do whatever calculations are required (including calling out to external servers) and return a database transsction encapsulating the result of the operation. Other operations that are using the output from this operation can take the output from this transaction and use it for their input.

(defn operation-create-scene
  "GenOp: create a new, empty scene"
  [db operation-id parameters]
  [{:db/id -1
    :game-entity/type :gbs/scene
    :game-entity/uuid (str (random-uuid))
    :scene/name "generated greenfield scene"
    }])



;;;
;;; Design Moves
;;;




;;;
;;; Database stuff
;;;

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
