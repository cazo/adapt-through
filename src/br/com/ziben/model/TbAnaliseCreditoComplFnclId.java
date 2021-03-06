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

package br.com.ziben.model;

// Generated Oct 30, 2013 4:54:18 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbAnaliseCreditoComplFnclId generated by hbm2java
 */
@Embeddable
public class TbAnaliseCreditoComplFnclId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8956007082089169444L;

	private Short codfil;
	private Integer idanalise;
	private Date dtultmov;
	private Date hrultmov;
	private String creditApplicationCod;
	private String xmllog;
	private String mensagem1;
	private String mensagem2;

	public TbAnaliseCreditoComplFnclId() {
	}

	public TbAnaliseCreditoComplFnclId(Short codfil, Integer idanalise, Date dtultmov, Date hrultmov, String creditApplicationCod, String xmllog, String mensagem1,
			String mensagem2) {
		this.codfil = codfil;
		this.idanalise = idanalise;
		this.dtultmov = dtultmov;
		this.hrultmov = hrultmov;
		this.creditApplicationCod = creditApplicationCod;
		this.xmllog = xmllog;
		this.mensagem1 = mensagem1;
		this.mensagem2 = mensagem2;
	}

	@Column(name = "CODFIL", precision = 3, scale = 0)
	public Short getCodfil() {
		return this.codfil;
	}

	public void setCodfil(Short codfil) {
		this.codfil = codfil;
	}

	@Column(name = "IDANALISE", precision = 9, scale = 0)
	public Integer getIdanalise() {
		return this.idanalise;
	}

	public void setIdanalise(Integer idanalise) {
		this.idanalise = idanalise;
	}

	@Column(name = "DTULTMOV", length = 23)
	public Date getDtultmov() {
		return this.dtultmov;
	}

	public void setDtultmov(Date dtultmov) {
		this.dtultmov = dtultmov;
	}

	@Column(name = "HRULTMOV", length = 23)
	public Date getHrultmov() {
		return this.hrultmov;
	}

	public void setHrultmov(Date hrultmov) {
		this.hrultmov = hrultmov;
	}

	@Column(name = "CREDIT_APPLICATION_COD", length = 15)
	public String getCreditApplicationCod() {
		return this.creditApplicationCod;
	}

	public void setCreditApplicationCod(String creditApplicationCod) {
		this.creditApplicationCod = creditApplicationCod;
	}

	@Column(name = "XMLLOG", length = 2000)
	public String getXmllog() {
		return this.xmllog;
	}

	public void setXmllog(String xmllog) {
		this.xmllog = xmllog;
	}

	@Column(name = "MENSAGEM1", length = 300)
	public String getMensagem1() {
		return this.mensagem1;
	}

	public void setMensagem1(String mensagem1) {
		this.mensagem1 = mensagem1;
	}

	@Column(name = "MENSAGEM2", length = 300)
	public String getMensagem2() {
		return this.mensagem2;
	}

	public void setMensagem2(String mensagem2) {
		this.mensagem2 = mensagem2;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbAnaliseCreditoComplFnclId))
			return false;
		TbAnaliseCreditoComplFnclId castOther = (TbAnaliseCreditoComplFnclId) other;

		return ((this.getCodfil() == castOther.getCodfil()) || (this.getCodfil() != null && castOther.getCodfil() != null && this.getCodfil().equals(
				castOther.getCodfil())))
				&& ((this.getIdanalise() == castOther.getIdanalise()) || (this.getIdanalise() != null && castOther.getIdanalise() != null && this.getIdanalise().equals(
						castOther.getIdanalise())))
				&& ((this.getDtultmov() == castOther.getDtultmov()) || (this.getDtultmov() != null && castOther.getDtultmov() != null && this.getDtultmov().equals(
						castOther.getDtultmov())))
				&& ((this.getHrultmov() == castOther.getHrultmov()) || (this.getHrultmov() != null && castOther.getHrultmov() != null && this.getHrultmov().equals(
						castOther.getHrultmov())))
				&& ((this.getCreditApplicationCod() == castOther.getCreditApplicationCod()) || (this.getCreditApplicationCod() != null
						&& castOther.getCreditApplicationCod() != null && this.getCreditApplicationCod().equals(castOther.getCreditApplicationCod())))
				&& ((this.getXmllog() == castOther.getXmllog()) || (this.getXmllog() != null && castOther.getXmllog() != null && this.getXmllog().equals(
						castOther.getXmllog())))
				&& ((this.getMensagem1() == castOther.getMensagem1()) || (this.getMensagem1() != null && castOther.getMensagem1() != null && this.getMensagem1().equals(
						castOther.getMensagem1())))
				&& ((this.getMensagem2() == castOther.getMensagem2()) || (this.getMensagem2() != null && castOther.getMensagem2() != null && this.getMensagem2().equals(
						castOther.getMensagem2())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCodfil() == null ? 0 : this.getCodfil().hashCode());
		result = 37 * result + (getIdanalise() == null ? 0 : this.getIdanalise().hashCode());
		result = 37 * result + (getDtultmov() == null ? 0 : this.getDtultmov().hashCode());
		result = 37 * result + (getHrultmov() == null ? 0 : this.getHrultmov().hashCode());
		result = 37 * result + (getCreditApplicationCod() == null ? 0 : this.getCreditApplicationCod().hashCode());
		result = 37 * result + (getXmllog() == null ? 0 : this.getXmllog().hashCode());
		result = 37 * result + (getMensagem1() == null ? 0 : this.getMensagem1().hashCode());
		result = 37 * result + (getMensagem2() == null ? 0 : this.getMensagem2().hashCode());
		return result;
	}

}
