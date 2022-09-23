const StealthPlugin = require('puppeteer-extra-plugin-stealth')
const AnonPlugin = require('puppeteer-extra-plugin-anonymize-ua')
const {scrollPageToBottom} = require('puppeteer-autoscroll-down')
const facebookConfig = require("./facebook/FacebookConfig.js")

const puppeteer = require('puppeteer-extra')

puppeteer.use(AnonPlugin())
puppeteer.use(StealthPlugin())

async function getPosts(facebookId, contentType) {
    const browser = await puppeteer.launch({
        headless: true,
        slowMo: 65,
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
        waitUntil: 'networkidle0'
    });
    await waitFor(0, 1);
    await waitForAndClick(page);
    await page.waitForSelector(facebookConfig.POST_CLASS)
    await scrollAndWait(page);
    await scrollAndWait(page);
    await waitFor(2, 4);
    await page.waitForSelector(facebookConfig.SEE_MORE_SELECTOR)
    const seeMoreElHandleArray = await page.$$(facebookConfig.SEE_MORE_SELECTOR)

    for (const el of seeMoreElHandleArray) {
        await waitFor(1, 2);
        await el.click()
    }


    const texts = []
    await page.waitForSelector(facebookConfig.POST_CLASS)
    const postsElHandleArray = await page.$$(facebookConfig.POST_CLASS)
    for (const element of postsElHandleArray) {
        await waitFor(1, 2);
        let text = '';
        switch (contentType) {
            case "html":
                text = await page.evaluate(el => el.innerHTML, element);
                break;
            case "text":
                text = await page.evaluate(el => el.innerText, element);
                break;
            default:
                break;
        }
        texts.push(text);
    }

    await waitFor(2, 3)
    await browser.close();
    return texts;
}

async function selectClickAndWait(page, selector) {
    await page.waitForSelector(selector)
    const elHandleArray = await page.$$(selector)
    for (const el of elHandleArray) {
        await waitFor(1, 2);
        await el.click()
    }
}

async function waitFor(min, max) {
    let milliseconds = getRandomInt(min * 1000, max * 1000)
    await new Promise(r => setTimeout(r, milliseconds))
}

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min) + min); // The maximum is exclusive and the minimum is inclusive
}

async function randomScroll(page) {
    await scrollPageToBottom(page, {
        size: getRandomInt(900, 1000),
        delay: getRandomInt(700, 1000),
        stepsLimit: 1
    })
}

async function scrollAndWait(page) {
    await randomScroll(page);
    await waitFor(1, 3);
}


async function waitForAndClick(page) {
    await page.waitForSelector(facebookConfig.COOKIE_SELECTOR)
    await page.click(facebookConfig.COOKIE_SELECTOR);
}


module.exports = {getPosts}