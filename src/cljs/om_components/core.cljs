(ns om-components.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)


(defn alert-box
  "Bootstrap Alert div"
  [cursor owner]
  (reify om/IRender
    (render [_]
      (if (:show-validation-error? cursor)
        (dom/div #js {:className "alert alert-danger alert-dismissible" :role "alert"}
                 (dom/button #js{:className  "close"
                                 :aria-label "Close"
                                 :onClick    (fn [_]
                                               (om/transact! cursor :show-validation-error? (fn [_] false)))}
                             (dom/span #js{:className "glyphicon glyphicon-remove"} nil))
                 (dom/p nil (:message-text cursor)))))))




(defn panel
  "Bootstrap Panel div; expects cursor to contain:
      :panel-title -> String,
      :body -> nodes,
      (optional) :panel-class -> additional String class for panel div"
  [cursor owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className "col-md-6 col-md-offset-3"}
               (dom/div #js{:className (str "panel panel-default" " " (:panel-class cursor))}
                        (dom/div #js{:className "panel-heading"} (dom/h4 #js{:className "panel-title"} (:panel-title cursor)))
                        (dom/div #js {:className "panel-body"} (:body cursor)))))))
