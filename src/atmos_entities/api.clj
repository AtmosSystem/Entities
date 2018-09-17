(ns atmos-entities.api
  (:require [atmos-kernel.web.core :refer [json-web-app
                                           request-body]]
            [atmos-kernel.web.security.auth :refer [token-auth]]
            [atmos-kernel.web.route :refer [defatmos-route
                                            atmos-GET
                                            atmos-POST
                                            atmos-PUT
                                            atmos-DELETE]]
            [atmos-entities.core :refer :all]))


(def entities "entities")
(def entity "entity")
(def contacts "contacts")

(defatmos-route app-routes :entity
                (atmos-GET [entities] (get-all entities)
                           :authentication-needed? true)
                (atmos-GET [entity :id] (get-entity (Long. (str id)))
                           :authentication-needed? true)

                (atmos-POST [entities] (let [body (request-body request)]
                                         (update-entities (:method body) body))
                            :authentication-needed? true)

                (atmos-PUT [entity] (add-entity (request-body request))
                           :authentication-needed? true)
                (atmos-DELETE [entity :id] (remove-entity (Long. (str id)))
                              :authentication-needed? true)

                (atmos-GET [entity contacts :entity-id] (get-contacts (Long. (str entity-id)))
                           :authentication-needed? true)
                (atmos-POST [entity contacts] (update-contact (request-body request))
                            :authentication-needed? true)
                (atmos-PUT [entity contacts] (add-contact (request-body request))
                           :authentication-needed? true))

(def app (json-web-app app-routes token-auth))
