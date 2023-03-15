/*
 * Copyright (c) 2023 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.enaium.nurupoga.configuration

import cn.dev33.satoken.stp.StpInterface
import cn.enaium.nurupoga.mapper.PermissionMapper
import cn.enaium.nurupoga.mapper.RoleMapper
import cn.enaium.nurupoga.model.entity.RoleEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @author Enaium
 */
@Configuration
class SaTokenConfiguration {
    @Bean
    fun stpInterface(roleMapper: RoleMapper, permissionMapper: PermissionMapper): StpInterface? {
        return object : StpInterface {
            override fun getPermissionList(loginId: Any, loginType: String): List<String> {
                return permissionMapper.selectListByUserId(loginId.toString().toLong())
                    .map { it.name!! }.toList()
            }

            override fun getRoleList(loginId: Any, loginType: String): List<String> {
                val selectByUserId: RoleEntity? = roleMapper.selectByUserId(loginId.toString().toLong())
                return selectByUserId?.let { listOf(selectByUserId.name!!) } ?: emptyList()
            }
        }
    }
}