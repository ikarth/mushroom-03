(ns front.events
  (:require
   [front.state :refer [app-state]]
   [generator.gencore :as generator]
   ;;["clingo-wasm" :default clingo]
   )
  )

(defn set-active-sketch [active-sketch]
  (swap! app-state update-in [:active-sketch] active-sketch)
  )

;;(defn )

(defn init-database [event]
  (if (some? event)
    (.preventDefault event))
  (let [database-label :default-database]
    ;; make empty project
    (generator/make-empty-project database-label)
    
    (swap! app-state assoc-in [:data-internal] (generator/fetch-internal-view))
    (swap! app-state assoc-in [:data-input] (generator/fetch-input-view))
    (swap! app-state assoc-in [:data-output] (generator/fetch-output-view))
    (swap! app-state assoc-in [:data-nodes] (generator/fetch-node-view))
    ))

(defn update-database-view [event]
  (if (some? event)
    (.preventDefault event))
  (let []
    (swap! app-state assoc-in [:data-internal] (generator/fetch-internal-view))
    (swap! app-state assoc-in [:data-input]    (generator/fetch-input-view))
    (swap! app-state assoc-in [:data-output]   (generator/fetch-output-view))
    (swap! app-state assoc-in [:data-nodes]    (generator/fetch-node-view))
    ))
