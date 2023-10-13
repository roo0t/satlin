package time

object LeapSeconds {
    // Last updated on 13 Oct 2023
    private val leapSecondsByUt1Instants = listOf(
        Ut1Instant.ofNtpTimestamp(2272060800) to 10, // 1 Jan 1972
        Ut1Instant.ofNtpTimestamp(2287785600) to 11, // 1 Jul 1972
        Ut1Instant.ofNtpTimestamp(2303683200) to 12, // 1 Jan 1973
        Ut1Instant.ofNtpTimestamp(2335219200) to 13, // 1 Jan 1974
        Ut1Instant.ofNtpTimestamp(2366755200) to 14, // 1 Jan 1975
        Ut1Instant.ofNtpTimestamp(2398291200) to 15, // 1 Jan 1976
        Ut1Instant.ofNtpTimestamp(2429913600) to 16, // 1 Jan 1977
        Ut1Instant.ofNtpTimestamp(2461449600) to 17, // 1 Jan 1978
        Ut1Instant.ofNtpTimestamp(2492985600) to 18, // 1 Jan 1979
        Ut1Instant.ofNtpTimestamp(2524521600) to 19, // 1 Jan 1980
        Ut1Instant.ofNtpTimestamp(2571782400) to 20, // 1 Jul 1981
        Ut1Instant.ofNtpTimestamp(2603318400) to 21, // 1 Jul 1982
        Ut1Instant.ofNtpTimestamp(2634854400) to 22, // 1 Jul 1983
        Ut1Instant.ofNtpTimestamp(2698012800) to 23, // 1 Jul 1985
        Ut1Instant.ofNtpTimestamp(2776982400) to 24, // 1 Jan 1988
        Ut1Instant.ofNtpTimestamp(2840140800) to 25, // 1 Jan 1990
        Ut1Instant.ofNtpTimestamp(2871676800) to 26, // 1 Jan 1991
        Ut1Instant.ofNtpTimestamp(2918937600) to 27, // 1 Jul 1992
        Ut1Instant.ofNtpTimestamp(2950473600) to 28, // 1 Jul 1993
        Ut1Instant.ofNtpTimestamp(2982009600) to 29, // 1 Jul 1994
        Ut1Instant.ofNtpTimestamp(3029443200) to 30, // 1 Jan 1996
        Ut1Instant.ofNtpTimestamp(3076704000) to 31, // 1 Jul 1997
        Ut1Instant.ofNtpTimestamp(3124137600) to 32, // 1 Jan 1999
        Ut1Instant.ofNtpTimestamp(3345062400) to 33, // 1 Jan 2006
        Ut1Instant.ofNtpTimestamp(3439756800) to 34, // 1 Jan 2009
        Ut1Instant.ofNtpTimestamp(3550089600) to 35, // 1 Jul 2012
        Ut1Instant.ofNtpTimestamp(3644697600) to 36, // 1 Jul 2015
        Ut1Instant.ofNtpTimestamp(3692217600) to 37, // 1 Jan 2017
    )

    fun at(instant: Ut1Instant) = leapSecondsByUt1Instants.lastOrNull { instant >= it.first }?.second ?: 0
}
