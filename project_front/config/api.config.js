const isPro = Object.is(process.env.NODE_ENV, 'production')
module.exports = {

    baseUrl: isPro ? 'http://116.62.162.235:80/' : '/api'

}

