(ns atmos-entities.api
  (:require [atmos-kernel.web.ring :refer [def-json-web-api]]
            [atmos-kernel.core :refer [keyword-map]]
            [ring.middleware.defaults :refer [api-defaults]]
            [atmos-kernel.web.security.auth :refer [token-auth]]
            [atmos-kernel.web.route :refer [defatmos-routes
                                            atmos-main-route
                                            atmos-GET
                                            atmos-POST
                                            atmos-PUT
                                            atmos-DELETE]]
            [atmos-entities.core :refer :all]))


(def entities "entities")
(def entity "entity")
(def contacts "contacts")

(declare app app-routes request id)

(defatmos-routes app-routes
                 (atmos-main-route :entities)

                 (atmos-GET [entities entities] request
                            (get-all entities))

                 (atmos-GET [entities entity :id]
                            [id]
                            (get-entity (Long. (str id))))

                 (atmos-POST [entities entities] request
                             (let [body (keyword-map (request :params))]
                               (update-entities (body :method) body)))

                 (atmos-PUT [entities entity] request
                            (add-entity (keyword-map (request :params))))

                 (atmos-DELETE [entities entity :id]
                               [id]
                               (remove-entity (Long. (str id))))

                 (atmos-GET [entities entity contacts :id]
                            [id]
                            (get-contacts (Long. (str id))))

                 (atmos-POST [entities entity contacts] request
                             (update-contact (keyword-map (request :params))))

                 (atmos-PUT [entities entity contacts] request
                            (add-contact (keyword-map (request :params)))))

(def-json-web-api app app-routes api-defaults token-auth)
