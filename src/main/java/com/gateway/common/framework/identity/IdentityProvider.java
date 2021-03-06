/***********************************************************************
 * Module:  IdentityProvider.java
 * Author:  Zhang Huihua
 * Purpose: Defines the Interface IdentityProvider
 ***********************************************************************/

package com.gateway.common.framework.identity;

/** @pdOid 218a6632-d2ca-4255-b73e-643eba641586 */
public interface IdentityProvider {

	/**
	 * @param subject
	 * @pdOid 26ae022d-15ac-4415-be04-d8d86c652184
	 */
	public Identity createIdentity(String identityString);

}
