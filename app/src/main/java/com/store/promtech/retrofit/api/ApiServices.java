package com.store.promtech.retrofit.api;


import com.store.promtech.model.AboutUsmodel;
import com.store.promtech.model.AddToCart;
import com.store.promtech.model.AdvantageList;
import com.store.promtech.model.AllBanners;
import com.store.promtech.model.Banners;
import com.store.promtech.model.BaseResponse;
import com.store.promtech.model.Brand;
import com.store.promtech.model.CancelModel;
import com.store.promtech.model.CartDeleteAction;
import com.store.promtech.model.Category;
import com.store.promtech.model.Checkout;
import com.store.promtech.model.CheckoutText;
import com.store.promtech.model.ContactUsModel;
import com.store.promtech.model.DeaslsProduct;
import com.store.promtech.model.DeliveryText;
import com.store.promtech.model.HomeSlot;
import com.store.promtech.model.MyCart;
import com.store.promtech.model.MyOrders;
import com.store.promtech.model.MyOrdersDetailsModel;
import com.store.promtech.model.MyProfile;
import com.store.promtech.model.Notes;
import com.store.promtech.model.Notification;
import com.store.promtech.model.Offers;
import com.store.promtech.model.OrderCancellationModel;
import com.store.promtech.model.OrganicBenefit;
import com.store.promtech.model.PostSuggession;
import com.store.promtech.model.Privacymodel;
import com.store.promtech.model.ProductList;
import com.store.promtech.model.ProductListAcfold;
import com.store.promtech.model.ProductModel;
import com.store.promtech.model.LoginResponse;
import com.store.promtech.model.PushTest;
import com.store.promtech.model.ReferandEarnText;
import com.store.promtech.model.RefundModel;
import com.store.promtech.model.RegistrationResponse;
import com.store.promtech.model.Seasonal;
import com.store.promtech.model.Shippingmodel;
import com.store.promtech.model.SlidingProduct;
import com.store.promtech.model.Slot;
import com.store.promtech.model.SocialMedia;
import com.store.promtech.model.SubCategoryDataResponse;
import com.store.promtech.model.SubSubCategoryDataResponse;
import com.store.promtech.model.TermsConditionmodel;
import com.store.promtech.model.TimeZone;
import com.store.promtech.model.ViewSuggession;
import com.store.promtech.model.Referral;
import com.store.promtech.model.Wallet;
import com.store.promtech.model.WhyChoose;
import com.store.promtech.model.WhyChooseText;
import com.store.promtech.model.WhyChooseUsmodel;
import com.store.promtech.model.ZipCodeVerify;
import com.store.promtech.model.ZipCodemodel;
import com.store.promtech.productdialog.model.ReturnProductData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=return_list")
    Call<ReturnProductData> UserReturnedProducts(@Query("user_id") String user_id,
                                                 @Query("order_id") String order_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=login")
    Call<LoginResponse> UserLogin(@Query("phone") String phone, @Query("password") String password);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=otp_validation")
    Call<LoginResponse> VerifyOTP(@Query("otp") String otp, @Query("user_id") String user_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=registration_text")
    Call<AboutUsmodel> beforeRegistration();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=resend_otp")
    Call<LoginResponse> ResendOTP(@Query("phone") String phone);


//    @GET("service.php?action=registration")
//    Call<RegistrationResponse> UserRegistration(@Query("fname") String fname,
//                                                @Query("lname") String lname,
//                                                @Query("phone") String phone,
//                                                @Query("email") String email,
//                                                @Query("password") String password,
//                                                @Query("confirm_password") String confirm_password,
//                                                @Query("address") String address,
//                                                @Query("state") String state,
//                                                @Query("city") String city,
//                                                @Query("zip") String zip,
//                                                @Query("flag") String flag, @Query("unique_id") String unique_id,
//                                                @Query("ref_id") String referenceCode,
//                                                @Query("date_of_birth") String date_of_birth,
//                                                @Query("anniversary_date") String anniversary_date);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=registration")
    Call<RegistrationResponse> UserRegistration(@Query("name") String fname,
                                                @Query("phone") String phone,
                                                @Query("email") String email,
                                                @Query("password") String password,
                                                @Query("address") String address,
                                                @Query("state") String state,
                                                @Query("city") String city,
                                                @Query("zip") String zip,
                                                @Query("ref_code") String ref_code);

    //name=Saptarshi&email=saptarshi.mailme@gmail.com&phone=9007272180&address=abc&city=Kolkata&
    //state=WestBengal&zip=700085&gst=111&aadhar=123&back_name=aaa&branch_name=aaa&
    //acc_no=123456&ifsc=123456
    //is_aadhar_card_front_uploaded = 1 or 0
    //is_aadhar_card_back_uploaded = 1 or 0
    //is_gst_certificate_uploaded = 1 or 0
    //aadhar_card_front
    //aadhar_card_back
    //gst_certificate
    @Headers("Cache-control: no-cache")
    @POST("service.php?action=edit_profile")
    Call<BaseResponse> UserUpdateDetails(@Query("user_id") String user_id,
                                         @Query("name") String fname,
                                         @Query("email") String lname,
                                         @Query("phone") String phone,
                                         @Query("address") String address,
                                         @Query("city") String city,
                                         @Query("state") String state,
                                         @Query("zip") String zip,
                                         @Query("business_name") String business,
                                         @Query("gst_no") String gst
                                         );

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=change_password")
    Call<BaseResponse> ChangePassword(@Query("old_password") String old_password,
                                      @Query("new_password") String new_password,
                                      @Query("confirm_password") String confirm_password,
                                      @Query("user_id") String user_id);


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=forgot_password")
    Call<BaseResponse> UserForgotPassword(@Query("phone") String email);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=my_account")
    Call<MyProfile> GetMYProfile(@Query("user_id") String user_id);


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=category")
    Call<Category> GeCategory();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=delivery_time")
    Call<Notes> GetNotes();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=refer_earn")
    Call<TermsConditionmodel> GetReferEarn();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=banners")
    Call<Banners> GetBanners(@Query("user_id") String user_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=subcategory")
    Call<SubCategoryDataResponse> GeSubCategoryDataResponse(@Query("category_id") String category_id);

    //action=subsubcategory&subcategory_id=20
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=subsubcategory")
    Call<SubSubCategoryDataResponse> GeSubSubCategoryDataResponse(@Query("subcategory_id") String subcategory_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=product_list")
    Call<ProductListAcfold> GetProductListResponse(@Query("category_id") String category_id, @Query("subcategory_id") String subcategory_id, @Query("subsubcategory_id") String subsubcategory_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=featured_products")
    Call<ProductListAcfold> Getfeatured_products();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=advantage")
    Call<ProductListAcfold> Getadvantage();

/*
    @GET("coSZpazTAO?indent=2")
    Call<ProductList> GetProductListResponse(@Query("category_id") String category_id, @Query("subcategory_id") String subcategory_id, @Query("user_id") String user_id, @Query("unique_id") String unique_id);
*/

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=zipcode_list")
    Call<ZipCodemodel> GetZipCodeList();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=pet_food_list")
    Call<ProductModel> Getpet_food_list(@Query("category_id") String category_id, @Query("user_id") String user_id);


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=add_to_cart")
    Call<BaseResponse> AddtoCartService2(@Query("user_id") String user_id,
                                        @Query("unique_id") String unique_id,
                                        @Query("product_id") String product_id,
                                        @Query("seller_id") String seller_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=add_to_cart")
    Call<BaseResponse> AddtoCartService(@Query("user_id") String user_id,
                                        @Query("unique_id") String unique_id,
                                        @Query("product_id") String product_id,
                                        @Query("packet_id") String packet_id,
                                        @Query("quantity") String quantity,
                                        @Query("isCartAdd") String isCartAdd);
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=about_us")
    Call<AboutUsmodel> GetAboutUS();


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=our_contacts")
    Call<ContactUsModel> GetContactUs();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=order_cancellation")
    Call<CancelModel> GetcancelPolicy();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=order_cancellation")
    Call<OrderCancellationModel> Getorder_cancellation();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=replacement")
    Call<OrderCancellationModel> Getorder_Replacement();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=return_refund")
    Call<RefundModel> Getorder_Refund();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=terms")
    Call<TermsConditionmodel> GetTermData();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=privacy")
    Call<Privacymodel> GetPrivacyData();
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=shipping_policy")
    Call<Shippingmodel> GetshippingData();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=zipcode_availability")
    Call<ZipCodeVerify> VerifyZipCode(@Query("zip") String zip);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=add_to_cart")
    Call<AddToCart> AddToCart(@Query("user_id") String user_id, @Query("product_id") String product_id, @Query("packet_id") String packet_id, @Query("unique_id") String unique_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=view_cart")
    Call<MyCart> GetMyCart(@Query("user_id") String user_id, @Query("unique_id") String unique_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=return_request")
    Call<ZipCodeVerify> returnProduct(@Query("user_id") String user_id,@Query("order_id") String order_id, @Query("product_with_quantity") String product_with_quantity);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=my_orders")
    Call<MyOrders> GetMyOrdes(@Query("user_id") String user_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=delete_item")
    Call<CartDeleteAction> GetCartDeleteAction(@Query("user_id") String user_id, @Query("cart_id") String cart_id, @Query("unique_id") String unique_id);

    //Preferences.get_userId(mContext), cart_id, Preferences.get_UniqueId(mContext), quantity
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=update_item")
    Call<CartDeleteAction> UpdateMyCart(@Query("user_id") String user_id, @Query("cart_id") String cart_id, @Query("unique_id") String unique_id, @Query("quantity") String quantity, @Query("isCartAdd") String isCartAdd);

    /*@Headers("Cache-control: no-cache")
    @GET("service.php?action=checkout")
    Call<Checkout> PostShipping(@Query("name") String fname,

                                @Query("email") String email,
                                @Query("phone") String phone
            , @Query("address") String address
            , @Query("city") String city
            , @Query("state") String state
            , @Query("zip") String zip
            , @Query("user_id") String user_id
            , @Query("unique_id") String unique_id
            , @Query("quick_delivery") String quick_delivery
           ,@Query("pickup_date") String pickupDate,
                                @Query("pickup_time") String time,
                                @Query("delivery_type") String delivery_type,
                                @Query("landmark") String landmark); */

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=checkout")
    Call<Checkout> PostShipping(@Query("name") String fname,

                                @Query("email") String email,
                                @Query("phone") String phone
                                , @Query("address") String address
                                , @Query("city") String city
                                , @Query("state") String state
                                , @Query("zip") String zip
                                , @Query("user_id") String user_id
                                , @Query("unique_id") String unique_id
                                , @Query("business_name") String business
                                , @Query("gst_no") String gst);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=order_thankyou")
    Call<CartDeleteAction> GetCartThankyouMessage(@Query("user_id") String user_id, @Query("unique_id") String unique_id,
                                                  @Query("payment_method") String payment_method, @Query("payment_status") String payment_status,
                                                  @Query("transaction_id")String transaction_id);



    @Headers("Cache-control: no-cache")
    @GET("service.php?action=post_enquiry")
    Call<CartDeleteAction> PostEnquiry(@Query("user_id") String user_id, @Query("comment") String comment);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=post_feedback")
    Call<CartDeleteAction> PostFeedback(@Query("user_id") String user_id, @Query("comment") String comment, @Query("rate") String rate);


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=search_result")
    Call<ProductList> Getsearch_food_list(@Query("user_id") String user_id, @Query("unique_id") String unique_id, @Query("search_string") String search_string);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=search_result")
    Call<ProductListAcfold> Getsearch_food_listAc(@Query("user_id") String user_id, @Query("unique_id") String unique_id, @Query("search_string") String search_string);


    @Headers("Cache-control: no-cache")
    @GET("service.php?action=autopopulate_product_list")
    Call<ProductList> Getsearch_food_list2(@Query("user_id") String user_id, @Query("unique_id") String unique_id, @Query("search_string") String search_string);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=search_result")
    Call<ProductListAcfold> Getsearch_food_list1(@Query("category_id") String category_id, @Query("user_id") String user_id, @Query("unique_id") String unique_id, @Query("subcategory_id") String subcat_id, @Query("search_string") String search_string);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=order_details")
    Call<MyOrdersDetailsModel> GetOrderedProductDetails(@Query("user_id") String user_id, @Query("order_id") String order_id);
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=cancel_order_request")
    Call<BaseResponse> cancelOrder(@Query("user_id") String user_id, @Query("order_id") String order_id,@Query("request_comment") String request_comment);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=offers")
    Call<Offers>GetOffer();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=checkout_text")
    Call<CheckoutText>GetCheckoutText();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=post_suggestion")
    Call<PostSuggession> PostSuggession (@Query("user_id") String user_id,@Query("comment")String comment, @Query("order_id") String order_id);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=view_suggestion")
    Call<ViewSuggession> ViewSuggession (@Query("user_id") String user_id, @Query("order_id") String order_id);

    @Headers("Cache-control: no-cache")
    @GET("Asia/Kolkata")
    Call<TimeZone>GetTimeZone();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=advantage")
    Call<AdvantageList> GetMostSellingProduct ();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=brands")
    Call<Brand>GetBrand();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=homepage_photos")
    Call<AllBanners>GetAllBanners();

    @GET("service.php?action=organic_benefits")
    Call<OrganicBenefit> GetOeganicBenefit();

    //refer_earn&user_id=1
    @GET("service.php?action=refer_earn")
    Call<Referral> GetReferralBenefit(@Query("user_id") String userId);
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=delivery_text")
    Call<DeliveryText>GetDeliveryText();


    @GET("service.php?action=refer_earn")
    Call<ReferandEarnText>GetReferandEarnText();
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=why_choose")
    Call<WhyChooseText>GetWhyChooseText();
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=whychoose")
    Call<WhyChoose>GetWhyChoose();

    @GET("service.php?action=season_product_list")
    Call<Seasonal>GetSeasonalProduct(@Query("user_id") String userId, @Query("unique_id") String uniqueId);


    @GET("service.php?action=social_media")
    Call<SocialMedia>GetSocialMedia();

    @GET("service.php?action=deal_product_list")
    Call<DeaslsProduct> GetDealsProduct (@Query("user_id") String user_id, @Query("unique_id") String unique_id);
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=pickup_time")
    Call<Slot>GetSlot(@Query("pickup_date") String date);
    @GET("service.php?action=test_push")
    Call<PushTest>PushTest(@Query("user_id") String userId,@Query("comment") String comments);
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=home_delivery_slot")
    Call<HomeSlot>GetHomeSloat();
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=sliding_product_list")
    Call<SlidingProduct>GetSlidingProduct(@Query("user_id") String userId, @Query("unique_id") String uniqueid);

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=my_wallet")
    Call<Wallet> GetWalletBenefit(@Query("user_id") String userId);
    @Headers("Cache-control: no-cache")
    @GET("service.php?action=notification")
    Call<Notification> GetNotification();

    @Headers("Cache-control: no-cache")
    @GET("service.php?action=wcu")
    Call<WhyChooseUsmodel> GetWhyChooseUS();
}
