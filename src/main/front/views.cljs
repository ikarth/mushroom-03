(ns front.views
  (:require [front.state :refer [app-state]]
            [front.events :refer [init-database]]
            [reagent.core :as r]
            [reagent.dom :as rd]
            [cljs.pprint]
            [json-html.core :refer [json->hiccup json->html edn->html]]
            [coll-pen.core]
            [clojure.walk]
            [clojure.string]
            [goog.crypt :as crypt]
            [re-com.core :as rc]
            [re-com.box :as rc-box]
            [re-com.util :as rc-util]
            [quil.core :as qc]
            [quil.middleware :as qm]
            [cljs.core.async :as a]
            ))

(defn header
  []
  [:div
   [:h1 "Ecological Generator Output Visualization"]])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn app []
  (let []
    [:div
     [header]]


    ))
