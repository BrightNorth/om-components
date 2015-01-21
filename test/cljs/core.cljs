(ns om-components.test.core
  (:require-macros [cemerick.cljs.test :refer (is deftest with-test run-tests testing test-var)]
                   [dommy.core :refer [sel sel1]])
  (:require [cemerick.cljs.test]
            [om-components.test.common :refer [new-container!]]
            [om-components.core :refer [alert-box]]
            [dommy.core :refer [attr text]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn simulate-click-event
  "From https://github.com/levand/domina/blob/master/test/cljs/domina/test.cljs"
  [el]
  (let [document (.-document js/window)]
    (cond
     (.-click el) (.click el)
     (.-createEvent document) (let [e (.createEvent document "MouseEvents")]
                                (.initMouseEvent e "click" true true
                                                 js/window 0 0 0 0 0
                                                 false false false false 0 nil)
                                (.dispatchEvent el e))
     :default (throw "Unable to simulate click event"))))

;(defn simulate-click-event
;  "Doesn't use GClosure, to be more realistic"
;  [el]
;  (let [el (single-node el)
;        document (.-document js/window)]
;    (try
;      (cond
;        (.-click el) (.click el)
;        (.-createEvent document) (let [e (.createEvent document "MouseEvents")]
;                                   (.initMouseEvent e "click" true true
;                                     js/window 0 0 0 0 0
;                                     false false false false 0 nil)
;                                   (.dispatchEvent el e))
;        :default (throw "Unable to simulate click event"))
;      (catch Exception e (str "caught exception: " (.getMessage e))))
;    ))



(deftest alertbox-has-correct-message-text
  (let [c (new-container!)]
    (om/root alert-box {:message-text "message1"} {:target c})
    (is (= "message1" (text (sel1 c :div.alert))))))


(deftest alertbox-not-present-when-show-false
  (let [c (new-container!)]
    (om/root alert-box {:message-text "message2" :hidden? true} {:target c})
    (is (nil? (sel1 c :div.alert)))))

(deftest alertbox-not-present-when-click-close
  (let [c (new-container!)]
    (om/root alert-box {:message-text "message2" :hidden? false} {:target c})
    (println (:text (sel1 c :button.close)))
    (simulate-click-event (sel1 c :button.close))
    (.setTimeout js/window #(is (nil? (sel1 c :div.alert))) 5000)
    ))


(deftest alertbox-has-correct-alert-class
  (let [c (new-container!)
        _ (om/root alert-box {:message-text "message3"} {:target c})
        box (sel1 c :div.alert)
        clazz (attr box "class")]
    (is (= "alert alert-dismissible " clazz))))
