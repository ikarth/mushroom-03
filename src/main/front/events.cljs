(ns front.events
  (:require
   [front.state :refer [app-state]]
   ;;["clingo-wasm" :default clingo]
   )
  )

(defn set-active-sketch [active-sketch]
  (swap! app-state update-in [:active-sketch] active-sketch)
  )

(defn init-database [event]
  (if (some? event)
    (.preventDefault event))
  (let []
    )
  )

(defn update-database-view [event]
  (if (some? event)
    (.preventDefault event))
  (let []
    ))
