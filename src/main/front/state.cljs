(ns front.state
  (:require [reagent.core :refer [atom]]   
            ))


(defonce app-state
  (atom {:count 0
         :gbs-output nil
         :gbs-input nil}))
