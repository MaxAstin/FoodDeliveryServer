package com.bunbeauty.fooddelivery.service.init

import at.favre.lib.crypto.bcrypt.BCrypt
import com.bunbeauty.fooddelivery.data.Constants.MAIN_ADMIN_PASSWORD
import com.bunbeauty.fooddelivery.data.Constants.MAIN_ADMIN_USERNAME
import com.bunbeauty.fooddelivery.data.Constants.MAIN_CITY_NAME
import com.bunbeauty.fooddelivery.data.Constants.MAIN_COMPANY_NAME
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.city.InsertCity
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import com.bunbeauty.fooddelivery.data.model.user.InsertUser
import com.bunbeauty.fooddelivery.data.repo.city.ICityRepository
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.toxicbakery.bcrypt.Bcrypt

class InitService(
    private val companyRepository: ICompanyRepository,
    private val cityRepository: ICityRepository,
    private val userRepository: IUserRepository,
) : IInitService {

    override suspend fun initDataBase() {
        val mainCompany = createMainCompany()
        val mainCity = createMainCity(mainCompany.uuid)
        createMainAdmin(mainCity.uuid)
    }

    suspend fun createMainCompany(): GetCompany {
        val company = companyRepository.getCompanyByName(MAIN_COMPANY_NAME)
        return if (company == null) {
            val insertCompany = InsertCompany(
                name = MAIN_COMPANY_NAME,
                forFreeDelivery = 0,
                deliveryCost = 0
            )
            companyRepository.insertCompany(insertCompany)
        } else {
            company
        }
    }

    suspend fun createMainCity(companyUuid: String): GetCity {
        val city = cityRepository.getCityByCompanyUuidAndName(companyUuid.toUuid(), MAIN_CITY_NAME)
        return if (city == null) {
            val insertCity = InsertCity(
                name = MAIN_CITY_NAME,
                offset = 3,
                company = companyUuid.toUuid(),
                isVisible = true
            )
            cityRepository.insertCity(insertCity)
        } else {
            city
        }
    }

    suspend fun createMainAdmin(cityUuid: String) {
        val user = userRepository.getUserByUsername(MAIN_ADMIN_USERNAME)
        if (user == null) {
            val password = System.getenv(MAIN_ADMIN_PASSWORD)
            val passwordHash = String(Bcrypt.hash(password, BCrypt.MIN_COST))
            val insertUser = InsertUser(
                username = MAIN_ADMIN_USERNAME,
                passwordHash = passwordHash,
                role = UserRole.ADMIN,
                cityUuid = cityUuid.toUuid(),
            )
            userRepository.insertUser(insertUser)
        }
    }
}