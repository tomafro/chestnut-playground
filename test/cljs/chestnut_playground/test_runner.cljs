(ns chestnut-playground.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [chestnut-playground.core-test]
   [chestnut-playground.common-test]))

(enable-console-print!)

(doo-tests 'chestnut-playground.core-test
           'chestnut-playground.common-test)
