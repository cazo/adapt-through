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

// Generated Oct 30, 2013 12:33:40 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbAnaliseCreditoFnclId generated by hbm2java
 */
@Embeddable
public class TbAnaliseCreditoFnclId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2857393946673907283L;
	
	private short codfil;
	private int idanalise;
	private short idproduto;

	public TbAnaliseCreditoFnclId() {
	}

	public TbAnaliseCreditoFnclId(short codfil, int idanalise, short idproduto) {
		this.codfil = codfil;
		this.idanalise = idanalise;
		this.idproduto = idproduto;
	}

	@Column(name = "codfil", nullable = false, precision = 3, scale = 0)
	public short getCodfil() {
		return this.codfil;
	}

	public void setCodfil(short codfil) {
		this.codfil = codfil;
	}

	@Column(name = "idanalise", nullable = false, precision = 9, scale = 0)
	public int getIdanalise() {
		return this.idanalise;
	}

	public void setIdanalise(int idanalise) {
		this.idanalise = idanalise;
	}

	@Column(name = "idproduto", nullable = false, precision = 3, scale = 0)
	public short getIdproduto() {
		return this.idproduto;
	}

	public void setIdproduto(short idproduto) {
		this.idproduto = idproduto;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbAnaliseCreditoFnclId))
			return false;
		TbAnaliseCreditoFnclId castOther = (TbAnaliseCreditoFnclId) other;

		return (this.getCodfil() == castOther.getCodfil()) && (this.getIdanalise() == castOther.getIdanalise()) && (this.getIdproduto() == castOther.getIdproduto());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodfil();
		result = 37 * result + this.getIdanalise();
		result = 37 * result + this.getIdproduto();
		return result;
	}

}
