(ns om-components.test.core
  (:require-macros [cemerick.cljs.test :refer (is deftest with-test run-tests testing test-var)])
  (:require [cemerick.cljs.test]
            [om-components.test.common :refer [new-container!]]
            [om-components.core :refer [alert-box]]
            [dommy.core :refer-macros [sel sel1]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))


(deftest alertbox-displays-when-flag-set
  (let [c (new-container!)]
    (om/root alert-box {:show? true :message-text "Now you see me..."} {:target c})
    (is (sel1 :div.alert))))


(deftest alertbox-does-not-display-when-flag-not-set
  (let [c (new-container!)]
    (om/root alert-box {:show? false :message-text "... now you don't!"} {:target c})
    (is (nil? (sel1 :div.alert)))))
