(ns om-components.test.core
  (:require-macros [cemerick.cljs.test :refer (is deftest with-test run-tests testing test-var)]
                   [dommy.core :refer [sel sel1]])
  (:require [cemerick.cljs.test]
            [om-components.test.common :refer [new-container!]]
            [om-components.core :refer [alert-box]]
            [dommy.core :refer [attr text]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))




(deftest alertbox-has-correct-message-text
  (let [c (new-container!)]
    (om/root alert-box {:message-text "message1"} {:target c})
    (is (= "message1" (text (sel1 c :div.alert))))))


(deftest alertbox-not-present-when-show-false
  (let [c (new-container!)]
    (om/root alert-box {:message-text "message2" :hidden? true} {:target c})
    (is (nil? (sel1 c :div.alert)))))


(deftest alertbox-has-correct-alert-class
  (let [c (new-container!)
        _ (om/root alert-box {:message-text "message3"} {:target c})
        box (sel1 c :div.alert)
        clazz (attr box "class")]
    (is (= "alert alert-dismissible " clazz))))
