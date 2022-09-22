const StealthPlugin = require('puppeteer-extra-plugin-stealth')
const AnonPlugin = require('puppeteer-extra-plugin-anonymize-ua')
const {scrollPageToBottom} = require('puppeteer-autoscroll-down')
const facebookConfig = require("./facebook/FacebookConfig.js")

const puppeteer = require('puppeteer-extra')

puppeteer.use(AnonPlugin())
puppeteer.use(StealthPlugin())

const getPosts = async (facebookId) => {
    const browser = await puppeteer.launch({
        headless: true,
        slowMo: 50,
        args: ['--lang=en-GB']
    });

    let page = await browser.newPage();
    await page.setViewport({
        width: 1920,
        height: 1080,
        deviceScaleFactor: 1,
    });

    await page.evaluateOnNewDocument(() => {
        Object.defineProperty(navigator, "language", {
            get: function () {
                return "en-EN";
            }
        });
        Object.defineProperty(navigator, "languages", {
            get: function () {
                return ["en-EN", "en"];
            }
        });
    });
    await page.goto((facebookConfig.FACEBOOK_BASE_URL + facebookId), {
        waitUntil: 'networkidle2'
    });

    await page.click(facebookConfig.COOKIE_SELECTOR);

    const lastPosition = await scrollPageToBottom(page, {
        size: 1000,
        delay: 500,
        stepsLimit: 2
    })


    await page.$$eval(facebookConfig.SEE_MORE_SELECTOR, elHandles => elHandles.forEach(el => el.click()))
    await page.waitForSelector(facebookConfig.POST_CLASS)
    const texts = await page.$$eval(facebookConfig.POST_CLASS,
        divs => divs.map(({ innerText }) => innerText));

    await browser.close();
    return texts;
}
module.exports = {getPosts}