
const isPro = Object.is(process.env.NODE_ENV, 'production')



console.log(isPro);



module.exports = {

    baseUrl: isPro ? 'http://116.62.162.235:80/' : '/api'

}

console.log(isPro);
