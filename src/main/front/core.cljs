(ns front.core
  (:require [reagent.dom :as r]
            [front.views :as views]
            [front.events :as events]
            ;; generator
            ;; shadow.resource
            ))

(js/console.log "Ecological Generator Front End v003, 2022")

(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (let []
    (events/init-database nil)
    (events/update-database-view nil)
    (r/render [views/app]
              (.getElementById js/document "app"))))


; This is the `ecological.core.init()` that's triggered in the html
(defn ^:export init []
  
  (start))

