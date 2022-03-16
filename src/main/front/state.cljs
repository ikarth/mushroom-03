(ns front.state
  (:require [reagent.core :refer [atom]]   
            ))


(defonce app-state
  (atom {:count 0
         :data-internal nil
         :data-nodes nil
         :data-output nil
         :data-input nil}))
