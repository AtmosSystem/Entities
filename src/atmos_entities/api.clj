(ns atmos-entities.api
  (:require [atmos-kernel.web.core :refer [json-web-app]]
            [atmos-kernel.web.core :refer [request-body]]
            [atmos-kernel.web.route :refer [defatmos-route
                                            atmos-GET
                                            atmos-POST
                                            atmos-PUT
                                            atmos-DELETE]]
            [atmos-entities.service :refer :all]))


(def entities "entities")
(def entity "entity")
(def contacts "contacts")

(defatmos-route app-routes :entity

                (atmos-GET [entities] (get-entities*))
                (atmos-GET [entity :id] (get-entities* (Long. (str id))))

                (atmos-POST [entities] (let [body (request-body request)
                                             method (-> body :method keyword)]
                                         (cond
                                           (= method :get) (get-entities* body)
                                           (= method :delete) (str (remove-entities* body))
                                           (= method :update) (str (update-entities* body)))))

                (atmos-PUT [entity] (add-entities* (request-body request)))
                (atmos-DELETE [entity :id] (str (remove-entities* (Long. (str id)))))

                (atmos-GET [entity contacts :entity-id] (get-contacts* (Long. (str entity-id))))
                (atmos-POST [entity contacts] (str (update-contacts* (request-body request))))
                (atmos-PUT [entity contacts] (add-contacts* (request-body request))))

(def app (json-web-app app-routes))


