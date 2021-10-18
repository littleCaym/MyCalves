package com.example.calfcounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myNewDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Таблица Дневного обхода
        db.execSQL("create table dayList ("
                +"id integer primary key autoincrement,"
                +"date INT,"
                +"id_animal integer,"
                +"id_ration integer,"
                +"id_recept integer"
                +");");

        //Таблица животных:
        db.execSQL("create table myAnimals ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "birth INT," //
                + "weight real,"
                + "stat_zdor integer," // 0 - здоров 1 - болен
                + "id_illness integer,"
                + "date_start_treat INT"
                + ");");

        //Таблица продуктов:
        db.execSQL("create table myWareHouse ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "price text,"
                + "amount INT,"
                + "status integer"
                + ");");

        //Таблица рецептов:
        db.execSQL("create table myRecepts ("
                + "id integer primary key autoincrement,"
                + "illness_id integer,"
                + "med1_id integer,"
                + "med1_dose real,"
                + "med2_id integer,"
                + "med2_dose real,"
                + "continue_treat integer"
                + ");");

        //Таблица рационов:
        db.execSQL("create table rations ("
                + "id integer primary key autoincrement,"  // возраст животного - он идентичен id
                + "continue text,"
                + "prod1_id integer,"
                + "prod1_portion integer,"
                + "prod2_id integer,"
                + "prod2_portion integer,"
                + "prod3_id integer,"
                + "prod3_portion integer,"
                + "prod4_id integer,"
                + "prod4_portion integer,"
                + "prod5_id integer,"
                + "prod5_portion integer,"
                + "prod6_id integer,"
                + "prod6_portion integer,"
                + "prod7_id integer,"
                + "prod7_portion integer"
                + ");");

        //Таблица лекарств
        db.execSQL("create table aptechka ("
                + "id integer primary key autoincrement,"
                + "illness text,"
                + "price text,"
                + "amount float,"
                + "status integer"
                +");");

        //Таблица болезней
        db.execSQL("create table illnesses ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "symptoms text,"
                + "description text,"
                + "treatment text,"
                + "med1_id integer,"
                + "med2_id integer"
                +");");


        setStartDayListList(db);
        setStartIllnessesList(db);
        setStartMedicinesList(db);
        setStartAnimalsList(db);
        setStartWareHouseList(db);
        setStartReceptsList(db);
        setStartRationsList(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void setStartDayListList(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("date",0);
        db.insert("dayList", null,cv);
    }

    public void setStartAnimalsList(SQLiteDatabase db){

        ContentValues cv = new ContentValues();
        cv.put("name", "Ваня");
        long date2 = new Date().getTime() - 20*1000*60*60*24;
        cv.put("birth", date2);
        cv.put("weight", 150);
        cv.put("stat_zdor", 0);
        db.insert("myAnimals", null, cv);

        cv = new ContentValues();
        cv.put("name", "Боря");
        long date3 = new Date().getTime() - 20*1000*60*60*24 - 20*1000*60*60*24 - 20*1000*60*60*24 - 20*1000*60*60*24 - 20*1000*60*60*24 ;
        cv.put("birth", date2);
        cv.put("weight", 200);
        cv.put("stat_zdor", 0);
        db.insert("myAnimals", null, cv);

        cv = new ContentValues();
        cv.put("name", "Олег");
        cv.put("birth", new Date().getTime() - 5*1000*60*60*24);
        cv.put("weight", 50);
        cv.put("stat_zdor", 0);
        db.insert("myAnimals", null, cv);

    }

    public void setStartIllnessesList(SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put("name", "Колибактериоз");
        cv.put("symptoms", "недомогание, телята трудно поднимаются на ноги; \n" +
                "утраченный аппетит;\n" +
                "желтовато окрашенные, иногда с кровью, фекалии;\n" +
                "обезвоживание");
        cv.put("description","Патология вызывается постоянным обитателем \n" +
                "пищеварительного канала — кишечной палочкой \n" +
                "(эшерихией), поражающей особей со слабой защитной \n" +
                "системой. Другое наименование — белый понос подсосных \n" +
                "телят. Различают острое и перманентное протекание \n" +
                "болезни. Развитию манифестной формы предшествует \n" +
                "неудовлетворительное содержание и питание матерей, \n" +
                "трудный отел, несвоевременное выпаивание \n" +
                "молозива теленку.");
        cv.put("treatment", "Левомицетин задают больным телятам на первый прием \n" +
                "по 20мг на 1кг веса животного, а затем через каждые 8-12 часов телятам \n" +
                "по 15мг, ягнятам по 20мг на 1кг веса животного.\n" +
                "При лечение колибактериоза применяется гипериммунная сыворотка, \n" +
                "которую рекомендуется применять совместно с антибиотиками. \n" +
                "Сыворотку больным телятам вводят под кожу в дозе 50-60 мл.\n" +
                "Для удаления токсических и гнилостных веществ из кишечника \n" +
                "рекомендуются глубокие клизмы.\n");
        cv.put("med1_id", 1);
        cv.put("med2_id", 2);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Паратиф");
        cv.put("symptoms", "Запор, сменяющийся диареей. Кал кашеподобной консистенции.\n" +
                "Метеоризм.\n" +
                "Гипертермия, 41 °C.\n" +
                "Пневмония, сопровождающаяся сухим кашлем, из носа течет.\n" +
                "Шаткая походка теленка.\n" +
                "Болезни суставов ног.\n" +
                "Нервные явления.\n" +
                "Всклокоченная шерсть у телят.");
        cv.put("description","Возбудителем заболевания у телят считается \n" +
                "сальмонелла, небезопасная для человека, \n" +
                "животных других видов. Болеют ослабленные \n" +
                "детеныши до девятинедельного возраста. \n" +
                "Сальмонеллез характеризуется высокой \n" +
                "летальностью");
        cv.put("treatment", "Также хорошо зарекомендовал себя бактериофаг и для лечения, и в качестве \n" +
                "профилактического средства. Его необходимо давать телятам пероральным \n" +
                "способом в рекомендованной дозировке до 50 мл около двух – трех раз в день. \n" +
                "Если заболевание носит сложный характер, дозу можно увеличить вдвое.\n" +
                "\n" +
                "Первым делом необходимо изолировать заболевшего теленка от остального стада, \n" +
                "провести тщательную обработку помещения, в котором он находился при помощи \n" +
                "средств дезинфекции, и вызвать ветеринарного врача.\n");
        cv.put("med1_id", 3);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Коронавирусный энтерит");
        cv.put("symptoms", "гипертермия;\n" +
                "анорексия;\n" +
                "сердечная несостоятельность;\n" +
                "пневмония, отек легких;\n" +
                "кровавый понос;\n" +
                "отеки подчелюстной области, а также живота и подгрудка;\n" +
                "при длительном течении заболевания у телят опухают суставы ног.");
        cv.put("description","Заболевание поражает животных всех возрастов. \n" +
                "У телят пастереллез (геморрагическая септицимия) \n" +
                "возникает, преимущественно, летом. \n" +
                "Выделяют следующие формы заболевания:\n" +
                "септическую;\n" +
                "легочную;\n" +
                "кишечную;\n" +
                "отечную.\n" +
                "По скорости развития симптомов отличают \n" +
                "молниеносную, манифестную, подострую и перманентную\n" +
                "разновидность заболевания. В некоторых случаях гибель \n" +
                "наступает внезапно, прежде чем становятся \n" +
                "заметными признаки болезни.");
        cv.put("treatment", "Специфическая серотерапия эффективна только в первые дни \n" +
                "после возникновения признаков болезни.\n" +
                "Основными лекарственными средствами являются сульфаниламиды, \n" +
                "пролонгированные антибиотики, например, Нитокс, \n" +
                "лечение которым состоит из единственного укола.\n" +
                "\n" +
                "Заниматься лечением должен только квалифицированный специалист!!!!!");
        cv.put("med1_id", 4);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Анаэробная дизентерия");
        cv.put("symptoms", "Характеризуется геморрагической диареей. \n" +
                "В начале болезни у телят наблюдают гипертермию. \n" +
                "Грозным признаком считают падение температуры \n" +
                "ниже норматива.");
        cv.put("description","Острое заболевание новорожденных телят.\n" +
                "Заболевают гипотрофики, получая молоко зараженных \n" +
                "клостридиями коров, особенно если \n" +
                "их вымя не должным образом подготавливают к доению.");
        cv.put("treatment", "Лечение эффективно в начальной стадии болезни. \n" +
                "Применяют антитоксическую сыворотку против анаэробной дизинтерии и \n" +
                "инфекционной энтеротоксемии, которую вводят подкожно \n" +
                "в дозе 200—400 АЕ 2 раза в день.");
        cv.put("med1_id",5);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Пастереллез");
        cv.put("symptoms", "гипертермия;\n" +
                "анорексия;\n" +
                "сердечная несостоятельность;\n" +
                "пневмония, отек легких;\n" +
                "кровавый понос;\n" +
                "отеки подчелюстной области, а также живота и подгрудка;\n" +
                "при длительном течении заболевания у телят опухают суставы ног.");
        cv.put("description","Заболевание поражает животных всех возрастов. \n" +
                "У телят пастереллез (геморрагическая септицимия) \n" +
                "возникает, преимущественно, летом. \n" +
                "Выделяют следующие формы заболевания:\n" +
                "септическую;\n" +
                "легочную;\n" +
                "кишечную;\n" +
                "отечную.\n" +
                "По скорости развития симптомов отличают \n" +
                "молниеносную, манифестную, подострую и перманентную\n" +
                "разновидность заболевания. В некоторых случаях гибель \n" +
                "наступает внезапно, прежде чем становятся \n" +
                "заметными признаки болезни.");
        cv.put("treatment", "Специфическая серотерапия эффективна только в первые дни \n" +
                "после возникновения признаков болезни.\n" +
                "Основными лекарственными средствами являются сульфаниламиды, \n" +
                "пролонгированные антибиотики, например, Нитокс, \n" +
                "лечение которым состоит из единственного укола.\n" +
                "\n" +
                "Заниматься лечением должен только квалифицированный специалист!!!!!");
        cv.put("med1_id",6);

        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Криптоспоридиоз");
        cv.put("symptoms", "утрачивается аппетит;\n" +
                "регистрируют скрипение зубами;\n" +
                "возникает диарея;\n" +
                "развивается дегидратация;\n" +
                "теленок стремительно худеет.");
        cv.put("description","Возбудителем болезни телят является простейший паразит \n" +
                "из рода кокцидий, который не обладает \n" +
                "видоспецифичностью и представляет опасность \n" +
                "для человека. Заболевают детеныши молочного возраста и \n" +
                "молодняк до 8 месяцев. Заражение происходит там, \n" +
                "где зоогигиенические требования соблюдаются \n" +
                "не в полной мере.");
        cv.put("treatment", "толтарокс в той же дозе и прибиотик иммунобактерин-D («Укрзооветпромпостач»), \n" +
                "который содержит пробиотические бактерии Bacillus licheniformis, \n" +
                "Bacillus subtilis в дозе 10 грамм в сутки (по 5 грамм два раза в сутки) с молоком, \n" +
                "двое суток подряд");
        cv.put("med1_id",7);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Стрептококкоз");
        cv.put("symptoms", "гипертермия;\n" +
                "ринит;\n" +
                "характерные для отека легких назальные пенистые выделения;\n" +
                "одышка;\n" +
                "бронхиальные хрипы;\n" +
                "цианоз слизистых;\n" +
                "конъюнктивит — телята могут стать слепыми;\n" +
                "патологии алиментарного тракта;\n" +
                "артриты ног.");
        cv.put("description","Гемолитический диплокок (стрептококк) попадает в организм \n" +
                "теленка с рождения и представляет опасность \n" +
                "до 25-недельного возраста. Он внедряется алиментарным \n" +
                "либо аэрогенным способом. Контагий размножается там, \n" +
                "куда попал, проникает в лимфу или кровоток и разносится \n" +
                "по телу. Вредоносность микроба состоит в его \n" +
                "метаболитах — токсинах, угнетающих иммунную систему \n" +
                "теленка и выключающих механизмы свертывания крови. \n" +
                "Заболевание способно протекать во всевозможных формах \n" +
                "от молниеносной до хронической.");
        cv.put("treatment", "Гипериммунная сыворотка против стрептококкоза, которую вводят в/м в область \n" +
                "внутренней поверхности бедра телятам в дозе 50-100 мл\n" +
                "\n" +
                "При тяжелом течении болезни введение сыворотки повторяют через 12-24 часа.");
        cv.put("med1_id",8);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Диктиокаулез");
        cv.put("symptoms", "Заболевание проявляется кашлем, который постоянно усиливается. \n" +
                "Возникает одышка, скрипение зубами, назальные истечения. \n" +
                "Теленок выглядит вялым.");
        cv.put("description","Паразит обитает в легких, травмирует их паренхиму\n" +
                " и заносит вторичную микрофлору, вызывающую воспаление. \n" +
                "Заражение телят происходит на пастбище.");
        cv.put("treatment", "Локсуран — 40%-ный водный раствор дитразина цитрата. \n" +
                "Применяют как дитразина цитрат, в дозе 1,25 мл на 10кг живой массы \n" +
                "крупному рогатому скоту");
        cv.put("med1_id",9);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Чесотка");
        cv.put("symptoms", " Возникают дерматиты, теленок расчесывает зудящие места, \n" +
                "разгрызает их, формируются трещины, \n" +
                "которые инфицируются секундарной микрофлорой.");
        cv.put("description","Заболевание вызывают зудневые клещи, \n" +
                "обитающие внутри кожи. \n" +
                "Их экскременты обладают аллергическим действием");
        cv.put("treatment", "Ивермек используется в качестве однократной внутремышечной инъекции. \n" +
                "Дозировка: 1 мл на 50 кг живой массы.");
        cv.put("med1_id",10);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Гастроэнтерит");
        cv.put("symptoms", "Телята поносят, скрежещут зубами, лежат, \n" +
                "страдают отсутствием аппетита.");
        cv.put("description","Причиной воспаления органов алиментарного тракта \n" +
                "становится отравление испорченными кормами.");
        cv.put("treatment", "вода неограничено 24 часа\n" +
                "клизма");
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Диспепсия");
        cv.put("symptoms", "Симптомы диспепсии напоминают признаки гастроэнтерита. \n" +
                "Развивается понос, возникает обезвоживание.");
        cv.put("description","Несварение желудка вызвано недоразвитостью ферментной \n" +
                "системы теленка, несвоевременной выпойкой молозива или \n" +
                "низким его качеством. Диспепсия поражает, \n" +
                "преимущественно гипотрофиков, полученных от матерей, \n" +
                "которых во время сухостоя кормили неправильно. \n" +
                "Чаще всего недуг возникает в начале весны, когда качество \n" +
                "грубых кормов снижается, сено бедно каротином. \n" +
                "Использование силоса с высокой концентрацией бутирата \n" +
                "нарушает рубцовое пищеварение, корова теряет \n" +
                "способность продуцировать качественное молозиво.\n" +
                "\n" +
                "В первоначальном секрете молочной железы содержатся \n" +
                "антитела, которые специальные клетки в кишечнике \n" +
                "пропускают в кровоток единожды в жизни. Если детеныш \n" +
                "получает молозиво с опозданием, в его организм успевает \n" +
                "проникнуть нежелательная микрофлора, \n" +
                "пищеварение извращается, что приводит \n" +
                "к возникновению диспепсии");
        cv.put("treatment", "Необходимо исключить кормление теленка на 12 часов и \n" +
                "давать пить физиологический раствор (как молозиво)");
        cv.put("med1_id",11);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Бронхопневмония");
        cv.put("symptoms", "гипертермия;\n" +
                "обильные назальные экскреты;\n" +
                "затрудненное дыхание;\n" +
                "сердечная несостоятельность;\n" +
                "осложнения в форме алиментарных расстройств.");
        cv.put("description","Патология наиболее опасна для телят не достигших \n" +
                "четырехнедельного возраста. Она развивается на фоне \n" +
                "других патологий и простуды при несоблюдении \n" +
                "рекомендуемых зоогигиенических параметров.");
        cv.put("treatment", "стрептомицин внутривенно в дозе 0,5 г (7-12 мг на 1 кг массы тела) после \n" +
                "растворения в 20 мл 0,9% физиологического раствора 1 раз в день в течение 3 суток.");
        cv.put("med1_id",11);
        cv.put("med2_id",12);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Бронхопневмония");
        cv.put("symptoms", "гипертермия;\n" +
                "обильные назальные экскреты;\n" +
                "затрудненное дыхание;\n" +
                "сердечная несостоятельность;\n" +
                "осложнения в форме алиментарных расстройств.");
        cv.put("description","Патология наиболее опасна для телят не достигших \n" +
                "четырехнедельного возраста. Она развивается на фоне \n" +
                "других патологий и простуды при несоблюдении \n" +
                "рекомендуемых зоогигиенических параметров.");
        cv.put("treatment", "стрептомицин внутривенно в дозе 0,5 г (7-12 мг на 1 кг массы тела) после \n" +
                "растворения в 20 мл 0,9% физиологического раствора 1 раз в день в течение 3 суток.");
        cv.put("med1_id",13);
        cv.put("med2_id",14);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Тимпания");
        cv.put("symptoms", "В рубце формируется пена, препятствующая отрыжке. \n" +
                "Рубец раздувается.");
        cv.put("description","Развивается у телят старше двух месяцев, \n" +
                "когда у них начнет функционировать рубец. \n" +
                "Пастьба по бобовым растениям во время росы \n" +
                "либо инея вызывает чрезмерное газообразование.");
        cv.put("treatment", "Чтобы перевести газы в жидкое состояние, расслабить желудочные клапаны, \n" +
                "используют Тимпанол. В тяжелых случаях прокалывают стенку рубца троакаром \n" +
                "и осторожно выпускают избыток газов.\n" +
                "Чтобы адсорбировать скопившиеся газы, вводят:\n" +
                "\n" +
                "2-3 л парного молока;\n" +
                "200-300 г. древесного угля, смешанного с 2-3 л воды;\n" +
                "окись магния.");
        cv.put("med1_id",15);
        cv.put("med2_id",16);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Безоарная болезнь");
        cv.put("symptoms", "больные телята вылизывают шерсть друг у друга;\n" +
                "извращенный аппетит;\n" +
                "истощение;\n" +
                "вздутие живота;\n" +
                "потускнение шерсти;\n" +
                "предрасположенность к инфекционным заболеваниям.");
        cv.put("description","Безоары — это фито, трихо или казеиноконкременты, \n" +
                "образующиеся из комков пищи, отложившихся на \n" +
                "растительных волокнах поглощенной шерсти и \n" +
                "непереваренных комках молочного белка. Формируются при \n" +
                "резком переводе с одного корма на другой или \n" +
                "несбалансированном питании. Камни препятствуют \n" +
                "нормальному пищеварению, способны закупорить \n" +
                "кишечный проход.");
        cv.put("treatment", "Больным ягнятам дают коровье молоко по 150—200 мл в день. \n" +
                "Временное прекращение поедания шерсти ягнятами наступает после подкожного \n" +
                "введения в Течение 3—4 дней апоморфина в дозах 0,005—0,01 или при добавлении \n" +
                "7—10 капель настойки йода в молоке, обрат, воду раз в день");
        cv.put("med1_id",15);
        cv.put("med2_id",17);
        db.insert("illnesses", null, cv);

        cv = new ContentValues();
        cv.put("name", "Гиподерматоз");
        cv.put("symptoms", "Личинки обнаруживают по бугоркам на шкуре, \n" +
                "которые зудят, причиняют животным беспокойство, \n" +
                "снижают интенсивность роста");
        cv.put("description","Гиподерматоз — хронически протекающее опасное \n" +
                "заболевание крупного рогатого скота, вызываемое \n" +
                "подкожными оводами, личинки которых длительно, \n" +
                "в течение 6-9 месяцев, паразитируют в организме, \n" +
                "травмируют жизненно важные органы, \n" +
                "ткани и кожный покров животных, вызывая \n" +
                "снижение молочной и мясной продуктивности.\n");
        cv.put("treatment", "ивомек, цидектин и аверсект которые вводят подкожно в дозе 0,2мг/кг; фасковерм\n" +
                " — подкожно 1мл на 20кг живого веса, но не более 10мл на животное");
        cv.put("med1_id",18);
        db.insert("illnesses", null, cv);

    }

    public void setStartMedicinesList(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("illness", "ЛЕВОМИЦЕТИН");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "ГИПЕРИМУННАЯ СЫВОРОТКА");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "БАКТЕРИОФАГ");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "НАТРИЙ БИКАРБОНАТ");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "антитоксическая сыворотка");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "НИТОКС");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Укрзооветпромпостач");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "сыворотка против стрептококкоза");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Лоскуран");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Ивермек");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "ФИЗРАСТВОР");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "СТРЕПТОМИЦИН");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Тривит");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Тетравит");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Парное молоко");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Древесный уголь");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Апоморфин");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

        cv = new ContentValues();
        cv.put("illness", "Фасковерм");
        cv.put("amount",0);
        cv.put("status", 0);
        db.insert("aptechka", null, cv);

    }

    public void setStartWareHouseList(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("name", "Молозиво");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Поваренная соль");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Фосфатная подкормка");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Концентраты");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Сено");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Обрат");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Картофель");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Силос");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

        cv = new ContentValues();
        cv.put("name", "Сенаж");
        cv.put("amount", 0);
        cv.put("status", 0);
        db.insert("myWareHouse", null, cv);

    }

    public void setStartReceptsList(SQLiteDatabase db){

        ContentValues cv = new ContentValues();

        cv.put("illness_id", 1);
        cv.put("med1_id", 1);
        cv.put("med1_dose", 6);
        cv.put("med2_id", 2);
        cv.put("med2_dose", 60);
        cv.put("continue_treat", 2);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 2);
        cv.put("med1_id", 3);
        cv.put("med1_dose", 150);
        cv.put("continue_treat", 24);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 3);
        cv.put("med1_id", 4);
        cv.put("med1_dose", 13);
        cv.put("continue_treat", 14);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 4);
        cv.put("med1_id", 5);
        cv.put("med1_dose", 800);
        cv.put("continue_treat", 3);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 5);
        cv.put("med1_id", 6);
        cv.put("med1_dose", 20);
        cv.put("continue_treat", 14);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 6);
        cv.put("med1_id", 7);
        cv.put("med1_dose", 10);
        cv.put("continue_treat", 2);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 7);
        cv.put("med1_id", 8);
        cv.put("med1_dose", 100);
        cv.put("continue_treat", 7);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 8);
        cv.put("med1_id", 9);
        cv.put("med1_dose", 250);
        cv.put("continue_treat", 10);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 9);
        cv.put("med1_id", 10);
        cv.put("med1_dose", 40);
        cv.put("continue_treat", 2);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 10);
        cv.put("continue_treat", 10);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 11);
        cv.put("med1_id", 11);
        cv.put("med1_dose", 10000);
        cv.put("continue_treat", 5);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 12);
        cv.put("med1_id", 11);
        cv.put("med1_dose", 60);
        cv.put("med2_id", 12);
        cv.put("med2_dose", 0.5);
        cv.put("continue_treat", 10);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 13);
        cv.put("med1_id", 13);
        cv.put("med1_dose", 0.3);
        cv.put("med2_id", 14);
        cv.put("med2_dose", 0.3);
        cv.put("continue_treat", 30);
        db.insert("myRecepts", null, cv);

        cv.put("illness_id", 14);
        cv.put("med1_id", 15);
        cv.put("med1_dose", 3000);
        cv.put("med2_id", 16);
        cv.put("med2_dose", 300);
        cv.put("continue_treat", 4);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 15);
        cv.put("med1_id", 15);
        cv.put("med1_dose", 800);
        cv.put("med2_id", 17);
        cv.put("med2_dose", 10);
        cv.put("continue_treat", 4);
        db.insert("myRecepts", null, cv);

        cv = new ContentValues();
        cv.put("illness_id", 16);
        cv.put("med1_id", 18);
        cv.put("med1_dose", 10);
        cv.put("continue_treat", 1);
        db.insert("myRecepts", null, cv);


    }

    public void setStartRationsList(SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        for (int i = 1; i<=9; i++) {
            cv = new ContentValues();
            cv.put("continue", "1-9");
            cv.put("prod1_id", 1);
            cv.put("prod1_portion", 9000);
            db.insert("rations", null, cv);
        }

        for (int i = 10; i<=19; i++) {
            cv = new ContentValues();
            cv.put("continue", "10-19");
            cv.put("prod1_id", 1);
            cv.put("prod1_portion", 9000);
            cv.put("prod2_id", 2);
            cv.put("prod2_portion", 5);
            cv.put("prod3_id", 3);
            cv.put("prod3_portion", 5);
            db.insert("rations", null, cv);
        }

        for (int i = 20; i<=30; i++) {
            cv = new ContentValues();
            cv.put("continue", "20-30");
            cv.put("prod1_id", 1);
            cv.put("prod1_portion", 9000);
            cv.put("prod2_id", 2);
            cv.put("prod2_portion", 5);
            cv.put("prod3_id", 3);
            cv.put("prod3_portion", 5);
            cv.put("prod4_id", 4);
            cv.put("prod4_portion", 100);
            db.insert("rations", null, cv);
        }

        for (int i = 31; i<=42; i++) {
            cv = new ContentValues();
            cv.put("continue", "31-42");
            cv.put("prod1_id", 1);
            cv.put("prod1_portion", 9000);
            cv.put("prod2_id", 2);
            cv.put("prod2_portion", 10);
            cv.put("prod3_id", 3);
            cv.put("prod3_portion", 15);
            cv.put("prod4_id", 4);
            cv.put("prod4_portion", 200);
            cv.put("prod5_id", 5);
            cv.put("prod5_portion", 200);
            db.insert("rations", null, cv);
        }

        for (int i = 43; i<=59; i++) {
            cv = new ContentValues();
            cv.put("continue", "43-59");
            cv.put("prod1_id", 1);
            cv.put("prod1_portion", 5000);
            cv.put("prod2_id", 2);
            cv.put("prod2_portion", 10);
            cv.put("prod3_id", 3);
            cv.put("prod3_portion", 15);
            cv.put("prod4_id", 4);
            cv.put("prod4_portion", 700);
            cv.put("prod5_id", 5);
            cv.put("prod5_portion", 500);
            cv.put("prod6_id", 6);
            cv.put("prod6_portion", 5000);
            db.insert("rations", null, cv);
        }

        for (int i = 60; i<=70; i++) {
            cv = new ContentValues();
            cv.put("continue", "60-70");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1000);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 500);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 10000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 200);
            db.insert("rations", null, cv);
        }

        for (int i = 71; i<=80; i++) {
            cv = new ContentValues();
            cv.put("continue", "71-80");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1400);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 900);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 10000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 300);
            db.insert("rations", null, cv);
        }

        for (int i = 81; i<=90; i++) {
            cv = new ContentValues();
            cv.put("continue", "81-90");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1600);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 1000);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 10000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 500);
            db.insert("rations", null, cv);
        }

        for (int i = 91; i<=100; i++) {
            cv = new ContentValues();
            cv.put("continue", "91-100");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1500);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 1200);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 8000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 1500);
            db.insert("rations", null, cv);
        }

        for (int i = 101; i<=110; i++) {
            cv = new ContentValues();
            cv.put("continue", "101-110");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1500);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 1200);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 8000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 1500);
            cv.put("prod7_id", 8);
            cv.put("prod7_portion", 500);
            db.insert("rations", null, cv);
        }

        for (int i = 111; i<=120; i++) {
            cv = new ContentValues();
            cv.put("continue", "111-120");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1600);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 1500);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 8000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 1500);
            cv.put("prod7_id", 8);
            cv.put("prod7_portion", 1000);
            db.insert("rations", null, cv);
        }

        for (int i = 121; i<=140; i++) {
            cv = new ContentValues();
            cv.put("continue", "121-140");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 1800);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 1700);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 8000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 1000);
            cv.put("prod7_id", 8);
            cv.put("prod7_portion", 1000);
            db.insert("rations", null, cv);
        }

        for (int i = 141; i<=150; i++) {
            cv = new ContentValues();
            cv.put("continue", "141-150");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 10);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 15);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 2000);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 2000);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 4000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 1000);
            cv.put("prod7_id", 8);
            cv.put("prod7_portion", 1000);
            db.insert("rations", null, cv);
        }

        for (int i = 1511; i<=170; i++) {
            cv = new ContentValues();
            cv.put("continue", "151-170");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 20);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 25);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 2000);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 2500);
            cv.put("prod5_id", 6);
            cv.put("prod5_portion", 2000);
            cv.put("prod6_id", 7);
            cv.put("prod6_portion", 1000);
            cv.put("prod7_id", 8);
            cv.put("prod7_portion", 3000);
            db.insert("rations", null, cv);
        }

        for (int i = 171; i<=180; i++) {
            cv = new ContentValues();
            cv.put("continue", "171-180");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 20);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 25);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 2000);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 3000);
            cv.put("prod5_id", 7);
            cv.put("prod5_portion", 1000);
            cv.put("prod6_id", 8);
            cv.put("prod6_portion", 6000);
            db.insert("rations", null, cv);
        }

        for (int i = 181; i<=365; i++) {
            cv = new ContentValues();
            cv.put("continue", "181-365");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 20);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 40);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 2000);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 6000);
            cv.put("prod5_id", 8);
            cv.put("prod5_portion", 10000);
            cv.put("prod6_id", 9);
            cv.put("prod6_portion", 4000);
            db.insert("rations", null, cv);
        }

        for (int i = 366; i<=548; i++) {
            cv = new ContentValues();
            cv.put("continue", "366-548");
            cv.put("prod1_id", 2);
            cv.put("prod1_portion", 40);
            cv.put("prod2_id", 3);
            cv.put("prod2_portion", 50);
            cv.put("prod3_id", 4);
            cv.put("prod3_portion", 2000);
            cv.put("prod4_id", 5);
            cv.put("prod4_portion", 5000);
            cv.put("prod5_id", 8);
            cv.put("prod5_portion", 14000);
            cv.put("prod6_id", 9);
            cv.put("prod6_portion", 3000);
            db.insert("rations", null, cv);
        }

    }
}
