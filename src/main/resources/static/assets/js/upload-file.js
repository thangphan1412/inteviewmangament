import {Cloudinary} from "@cloudinary/url-gen";

$('#buttonSubmit').click(function (e) {
    console.log('upload');
    const cldInstance = new Cloudinary({
        cloud: {
            cloudName: 'dfkvp7x35',
            api_key: '536593263684142',
            api_secret: '29mquvkAuRme0JdrlNhdiI2NG60'
        }
    });


    // Upload file lên Cloudinary và trả về đường dẫn
    cldInstance.uploader.upload('https://images.unsplash.com/photo-1681953447837-34c2c0f0c36f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80', (error, result) => {
        if (error) {
            console.error(error);
        } else {
            console.log(result.secure_url);
        }
    });
});