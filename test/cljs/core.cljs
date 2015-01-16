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
    (om/root alert-box {:show-validation-error? true :message-text "Hello world"} {:target c})
    (is (sel1 :div.alert))))


(deftest alertbox-does-not-display-when-flag-not-set
  (let [c (new-container!)]
    (om/root alert-box {:show-validation-error? false :message-text "Hello world"} {:target c})
    (is (nil? (sel1 :div.alert)))))
