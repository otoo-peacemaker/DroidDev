package com.example.architecturaltemplate.util


interface Constants {
    interface Url {
        companion object {
            const val login =
                baseUrl + "login"
        }
    }

    interface FragmentTags {
        companion object {

            const val MOM = "Last MOM"
            const val WorkForce = "My team"
            const val Lead = "Lead"
            const val Pdf = "Pdf"
            const val Scheme = "Scheme"
            const val SchemeDescription = "SchemeDescription"
            const val ProductList = "Product List"
            const val FAQ = "FAQ"
            const val StoreSaleInSubmitFragment = "StoreSaleInSubmitFragment"
            const val StoreStockInHandFragment = "StoreStockInHandFragment"
            const val SaleLog = "Sale Out"
            const val Barcode = "Barcode"
            const val Support = "Support"
            const val StoreLocation = "Store Location"
        }
    }

    interface PreferenceConstants {
        companion object {
            const val Shared_Reach_Link = "shared reach link"
            const val MIN_DATE_NON_DSN = "mon date non dsn"
            const val DEVICE = "DEVICE"
            const val UPDATE_URI = "UPDATEURI"
            const val DeviceInfo = "DeviceInfo"

            // Login related
            const val USER_NAME = "username"
            const val TOKEN = "token"
            const val USER_PASSWORD = "password"
            const val USER_ID = "id"
            const val REG_ID = "Device_id"
            const val STATUS = "status"
            const val FIRST_NAME = "fname"
            const val POINTS = "points"
            const val LAST_NAME = "lname"
            const val TIER = "tier"
            const val UNIQUE_CODE = "uniquecode"
            const val ADDRESS_ONE = "add1"
            const val ADDRESS_TWO = "add2"
            const val CITY = "city"
            const val PINCODE = "pincode"
            const val LANDMARK = "landmark"
            const val STATE = "state"
            const val MOBILE = "mobile"
            const val Product = "Product"
            const val ATTENDANCE_LAST_DATE = "attendDate"
            const val ATTENDANCE_LAST_TIME = "attendTime"
            const val ATTENDANCE_STATUS = "attendanceStatus"
            const val STORE_LATITUDE = "store_lat"
            const val STORE_LONGITUDE = "store_long"
            const val STORE_ID = "store_id"
            const val STORE_NAME = "store"
            const val STORE_AddE = "store_long"
            const val MTD = "MTD"
            const val FULLDAY = "full_day"
            const val HALFDAY = "half_day"
            const val WEEKOFF = "week_off"
            const val CURRENTDATE = "current_date"
            const val PERCENTAGE = "percentage"
            const val ChangePasswordRequired = "ChangePasswordRequired"
            const val SENT_TOKEN_TO_SERVER = "sentTokenToServer"
            const val FCM_TOKEN = "FCMToken"
            const val NOTI_COUNT = "NotiCount"
            const val THEME = "THEME"
            const val Time = "Time"
            const val Role = "Role" //String USER_Type="USER_Type";
        }
    }


    companion object {
        const val EncryptKey = "6753598754127645"
        const val tokenKey = "rEDeMptIon"
        const val EQUAL = "="
        const val AMPERSAND = "&"
        val limitedDeviceInfo = StringBuffer()
        const val KeyToken = "jH4kB2aUDXF2OVp" //"t3Qo7xfdH1";

        const val usertypeARS = "ars"
        const val ERROR_CODE = "0"
        const val SUCCESS_CODE = "1"
        const val SUCCESS_MSG_CODE = "2"
        const val AUTHFAILURE_CODE = "-1"
        const val baseUrl = "http://addes.xyz/testing/api/"
        const val UpdateApk = "http://addes.xyz/testing/app/am.apk"
        const val UpdateVersion = "http://addes.xyz/testing/app/am_version.txt"
    }
}