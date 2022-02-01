package com.bunbeauty.fooddelivery.service.init

import at.favre.lib.crypto.bcrypt.BCrypt
import com.bunbeauty.fooddelivery.data.Constants.MAIN_ADMIN_PASSWORD
import com.bunbeauty.fooddelivery.data.Constants.MAIN_ADMIN_USERNAME
import com.bunbeauty.fooddelivery.data.Constants.MAIN_CITY_NAME
import com.bunbeauty.fooddelivery.data.Constants.MAIN_COMPANY_NAME
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.init.InitCompany
import com.bunbeauty.fooddelivery.data.model.cafe.InsertCafe
import com.bunbeauty.fooddelivery.data.model.category.InsertCategory
import com.bunbeauty.fooddelivery.data.model.city.GetCity
import com.bunbeauty.fooddelivery.data.model.city.InsertCity
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import com.bunbeauty.fooddelivery.data.model.menu_product.InsertMenuProduct
import com.bunbeauty.fooddelivery.data.model.street.InsertStreet
import com.bunbeauty.fooddelivery.data.model.user.InsertUser
import com.bunbeauty.fooddelivery.data.repo.cafe.ICafeRepository
import com.bunbeauty.fooddelivery.data.repo.category.ICategoryRepository
import com.bunbeauty.fooddelivery.data.repo.city.ICityRepository
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository
import com.bunbeauty.fooddelivery.data.repo.menu_product.IMenuProductRepository
import com.bunbeauty.fooddelivery.data.repo.street.IStreetRepository
import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.toxicbakery.bcrypt.Bcrypt

class InitService(
    private val companyRepository: ICompanyRepository,
    private val categoryRepository: ICategoryRepository,
    private val menuProductRepository: IMenuProductRepository,
    private val cityRepository: ICityRepository,
    private val userRepository: IUserRepository,
    private val cafeRepository: ICafeRepository,
    private val streetRepository: IStreetRepository,
) : IInitService {

    override suspend fun initDataBase() {
        val mainCompany = createMainCompany()
        val mainCity = createMainCity(mainCompany.uuid)
        createMainAdmin(mainCity.uuid)
    }

    override suspend fun initNewCompany(initCompany: InitCompany) {
        val insertCompany = InsertCompany(
            name = initCompany.name,
            forFreeDelivery = initCompany.forFreeDelivery,
            deliveryCost = initCompany.deliveryCost,
            forceUpdateVersion = initCompany.forceUpdateVersion,
        )
        val getCompany = companyRepository.insertCompany(insertCompany)

        val getCategoryList = initCompany.categoryList.map { initCategory ->
            val insertCategory = InsertCategory(
                name = initCategory.name,
                priority = initCategory.priority,
                companyUuid = getCompany.uuid.toUuid()
            )
            categoryRepository.insertCategory(insertCategory)
        }

        initCompany.menuList.onEach { initMenuProduct ->
            val insertMenuProduct = InsertMenuProduct(
                name = initMenuProduct.name,
                newPrice = initMenuProduct.newPrice,
                oldPrice = initMenuProduct.oldPrice,
                utils = initMenuProduct.utils,
                nutrition = initMenuProduct.nutrition,
                description = initMenuProduct.description,
                comboDescription = initMenuProduct.comboDescription,
                photoLink = initMenuProduct.photoLink,
                barcode = initMenuProduct.barcode,
                companyUuid = getCompany.uuid.toUuid(),
                categoryUuids = initMenuProduct.categories.mapNotNull { category ->
                    getCategoryList.find { getCategory ->
                        getCategory.name == category
                    }?.uuid?.toUuid()
                },
                isVisible = initMenuProduct.isVisible,
            )
            menuProductRepository.insertMenuProduct(insertMenuProduct)
        }

        initCompany.cityList.onEach { initCity ->
            val insertCity = InsertCity(
                name = initCity.name,
                timeZone = initCity.timeZone,
                company = getCompany.uuid.toUuid(),
                isVisible = initCity.isVisible,
            )
            val getCity = cityRepository.insertCity(insertCity)

            initCity.userList.onEach { initUser ->
                val insertUser = InsertUser(
                    username = initUser.username,
                    passwordHash = initUser.passwordHash,
                    role = initUser.role,
                    cityUuid = getCity.uuid.toUuid()
                )
                userRepository.insertUser(insertUser)
            }

            initCity.cafeList.onEach { initCafe ->
                val insertCafe = InsertCafe(
                    fromTime = initCafe.fromTime,
                    toTime = initCafe.toTime,
                    phone = initCafe.phone,
                    latitude = initCafe.latitude,
                    longitude = initCafe.longitude,
                    address = initCafe.address,
                    cityUuid = getCity.uuid.toUuid(),
                    isVisible = initCafe.isVisible,
                )
                val getCafe = cafeRepository.insertCafe(insertCafe)

                initCafe.streetList.onEach { street ->
                    val insertStreet = InsertStreet(
                        name = street,
                        cafeUuid = getCafe.uuid.toUuid(),
                        isVisible = true
                    )
                    streetRepository.insertStreet(insertStreet)
                }
            }
        }
    }

    suspend fun createMainCompany(): GetCompany {
        val company = companyRepository.getCompanyByName(MAIN_COMPANY_NAME)
        return if (company == null) {
            val insertCompany = InsertCompany(
                name = MAIN_COMPANY_NAME,
                forFreeDelivery = 0,
                deliveryCost = 0,
                forceUpdateVersion = 0,
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
                timeZone = "UTC+3",
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
                cityUuid = cityUuid.toUuid()
            )
            userRepository.insertUser(insertUser)
        }
    }


}