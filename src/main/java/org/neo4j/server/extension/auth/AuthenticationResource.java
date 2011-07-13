/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.extension.auth;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.neo4j.server.extension.auth.MultipleAuthenticationService.Permission.*;

@Path("/")
public class AuthenticationResource {

    private final MultipleAuthenticationService users;

    public AuthenticationResource(@Context MultipleAuthenticationService users) {
        this.users = users;
    }

    @GET @Path("/list")
    public Response listUsers() {
        return Response.status(OK).entity(users.getUsers()).build();
    }

    @POST @Path("/add-user-ro")
    public Response addUserRo(@FormParam("user") String user) {
        if (user == null) throw new IllegalArgumentException("missing parameter 'user'");
        users.setPermissionForUser(user, RO);
        return Response.status(OK).entity("OK").build();
    }

    @POST @Path("/add-user-rw")
    public Response addUserRw(@FormParam("user") String user) {
        if (user == null) throw new IllegalArgumentException("missing parameter 'user'");
        users.setPermissionForUser(user, RW);
        return Response.status(OK).entity("OK").build();

    }

    @POST @Path("/remove-user")
    public Response removeUser(@FormParam("user") String user) {
        if (user == null) throw new IllegalArgumentException("missing parameter 'user'");
        users.setPermissionForUser(user, NONE);
        return Response.status(OK).entity("OK").build();
    }
}