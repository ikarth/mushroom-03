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


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Quil Image Processing Stuff

;; Inspired by
;; https://github.com/simon-katz/nomisdraw/blob/master/src/cljs/nomisdraw/utils/nomis_quil_on_reagent.cljs
(defn q-sketch
  [& {:as sketch-args}]
  (let [host (if (not (contains? sketch-args :host))
               (str (random-uuid))
               (if (not (= nil (:host sketch-args)))
                 (:host sketch-args)
                 (str (random-uuid))))
        size (:size sketch-args)
        _ (assert (or (nil? size) (and (vector? size) (= (count size) 2)))
                  (str ":size should be nil or a vector of size 2, but instead it is " size))
        [w h] (if (nil? size) [600 600] size)
        canvas-id host 
        canvas-tag-&-id (keyword (str "div#" canvas-id))
        sketch-args* (merge sketch-args {:host canvas-id})
        saved-sketch-atom (atom ::not-set-yet)
        ]
    ^{:key canvas-id}
    [r/create-class
     {:reagent-render
      (fn []
        [canvas-tag-&-id {:style {:max-width w}
                          :width w
                          :height h}])
      :component-did-mount
      (fn []
        (a/go (reset! saved-sketch-atom (apply qc/sketch (apply concat sketch-args*)))))
      :component-will-unmount
      (fn []
        (a/go-loop [] ; will most likely not be reached
          (if (= @saved-sketch-atom ::not-set-yet)
            (do
              (a/<! (a/timeout 100))
              (recur))
            (qc/with-sketch @saved-sketch-atom
              (qc/exit)))))}]))

(defn visualize-image [img-data & {:keys [image-key use-canvas] :or {image-key (str (random-uuid)) use-canvas false}}]
  (if (undefined? img-data)
    (println "Tried to call (visualize-image) with undefined img-data.")
    (try
      (letfn [(img-size [] [(. img-data -width) (. img-data -height)])
              (initial-state []
                (let [[w h] (img-size)]
                  (qc/resize-sketch w h)
                  (qc/background-image img-data)
                  (qc/no-loop)
                  {:image img-data}))
              (update-state [state]
                state)
              (draw [state]
                (let [img (:image state)]))]
        (if (not use-canvas) ; can display as just the image or as an animated sketch
          (let [html-image-data (. (. img-data -canvas) toDataURL)]
            ^{:key image-key}
            [:img {:src html-image-data}])
          (q-sketch :setup initial-state
                    :update update-state
                    :draw draw
                    :host nil
                    :middleware [qm/fun-mode]
                    :size (img-size)
                    )))
      (catch :default e
        (js/console.log e)))))

(defn display-loose-images [g-state]
  (let [image (get "image-data" g-state)]
    (map (fn [i-entry]
           (if (map? i-entry)
             (let [idat (get i-entry "image-data")]
               (if (undefined? idat)
                 (println (str "Image data undefined for " i-entry))
                 (visualize-image idat {:key (get i-entry "uuid")})))))
         g-state)))

(defn visualize-generator-sketch
  ([w h]
   (visualize-generator-sketch w h nil))
  ([w h name]
   (letfn
       [(initial-state []
          (qc/pixel-density 1)
          {:time 0})
        (update-state [state] (update state :time inc))
        (draw [state]
          (let [t (:time state)]
            (qc/background 80 170 100)
            (qc/fill 100 120 230)
            (qc/ellipse (rem t w) (rem t h) 50 50)))]
     (q-sketch :setup initial-state
               :update update-state
               :draw draw
               :host name
               :middleware [qm/fun-mode]
               :size [w h]))))





;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn header
  []
  [:div
   [:h1 "Ecological Generator Output Visualization"]])

(defn display-output
  []
  [:div])

(defn display-generator []
  [:div])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn app []
  (let []
    [:div
     [header]
     [:div.dib.ma2.pa2.left-0.top-0.v-top
       (coll-pen.core/draw (:data-internal @app-state) {:key :pen-data-display :el-per-page 30 :truncate false})]
     [display-output]
     [display-generator]
     ;; TODO: find some other way to do image generation in the browser
     [#(visualize-generator-sketch 3 3 "quil-canvas")] ; NOTE: This is a load-bearing Quil/Processing sketch! Image generation ops don't work without some kind of canvas to use.   
     ]


    ))
