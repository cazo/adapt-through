/**
	This file is part of adapt-through.

    Adapt-through is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Adapt-through is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Adapt-through.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.ziben.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2013-10-29T20:29:16.807-02:00
 * Generated source version: 2.7.7
 * 
 */
@WebService(targetNamespace = "http://gestorvarejo.toolssoftware.com/wsdl/", name = "RetailProposalRegister")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RetailProposalRegister {

	@WebResult(name = "RetailProposalRegisterResponse", targetNamespace = "http://gestorvarejo.toolssoftware.com/wsdl/", partName = "RetailProposalRegisterResponse")
	@WebMethod(operationName = "RetailProposalRegister", action = "http://gestorvarejo.toolssoftware.com/wsdl/RetailProposalRegister")
	public java.lang.String retailProposalRegister(
			@WebParam(partName = "RetailProposalRegisterRequest", name = "RetailProposalRegisterRequest") java.lang.String retailProposalRegisterRequest);
}
