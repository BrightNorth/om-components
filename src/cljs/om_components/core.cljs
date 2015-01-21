(ns om-components.core
  (:require [goog.dom :as gdom]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)



(defn alert-box
  "Bootstrap Alert div; expects cursor to contain:
  :hidden? -> Should the alert be rendered at all?
  :message-text -> What's the alert text?
  (optional) :alert-class -> Additional CSS classes for the alert DIV"
  [cursor owner]
  (reify
    om/IRender
    (render [_]
      (if (not (:hidden? cursor))

          (dom/div #js {:className (str "alert alert-dismissible " (:alert-class cursor)) :role "alert"}
                   (dom/button #js{:className  "close"
                                   :aria-label "Close"
                                   :onClick    (fn [_] (om/transact! cursor :hidden? (fn [_] true)))}
                               (dom/span #js{:className "glyphicon glyphicon-remove"} nil))
                   (dom/p nil (:message-text cursor))))
      )))


(defn panel
  "Bootstrap Panel div; expects cursor to contain:
      :panel-title -> String,
      :body -> nodes,
      (optional) :container-class -> positioning classes for containing div
      (optional) :panel-class -> additional String class for panel div"
  [cursor owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className (:container-class cursor)}
               (dom/div #js{:className (str "panel panel-default" " " (:panel-class cursor))}
                        (dom/div #js {:className "panel-heading"} (dom/h4 #js{:className "panel-title"} (:panel-title cursor)))
                        (dom/div #js {:className "panel-body"} (:body cursor)))))))


(defn main
  "Main"
  []
  (om/root
   alert-box
   {}
   {:target (. js/document (getElementById "app"))}))
