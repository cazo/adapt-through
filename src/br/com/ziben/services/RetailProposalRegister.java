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
