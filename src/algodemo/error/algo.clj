(ns algodemo.error.algo
  (:require
   [taoensso.timbre :refer [info warn error]]))

(defn error-algo [_env opts bar-ds]
  (info "running error-algo: trailing-n: " (:trailing-n opts) " asset: " (:asset opts))
  (throw (Exception. "intentional error exception!")))

