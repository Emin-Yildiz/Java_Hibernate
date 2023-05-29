# ORM & HIBERNATE

## ORM (Object Relational Mapping)

- Nesne ilişkisel eşleme, ORM sanal olarak bir tablonun, oluşturduğumuz bir nesne ile eşleşmesidir. ORM veritabanı ve uygulama arasında ekstra bir veri katmanı (data layer) olarak yer alır.

  ### ORM Avantajları

   * ORM sayesinde uygulama veri tabanından bağımsız olur ve bu sayede ilerde başka bir veri tabanına geçiş olursa bu geçişin sorunsuz bir şekilde olmasını sağlar.

   * ORM nesneye yönelik programlamlanın sunduğu olanaklardan yararlanmamızı sağlar. Örnek olarak, kalıtım (inheritance) konsepti zahmetsiz bir şekilde veri tabanında eşlenebilir.

  ### ORM Dezavantajları

    * Yüksek performans gerektiren veya kompleks sorgular barındıran uygulamalarda, ORM sorguları optimize edilmezse düşük performans sunabilir.

  ### ORM Yaklaşımı 3'e ayrılır

    1. Database First Yakaşımı
       * Öncelikle veritabanının oluşturulması, sonrasında bu veritabanındaki tablolara karşılık gelecek sınıfların proje üzerinde oluşturulması yaklaşımıdır.

    2. Model First Yaklaşımı
       * Görsel arayüz ile veritabanı oluşturulup, sonrasında bu veritabanındaki tablolara karşılık gelecek sınıfların proje üzerinde oluşturulması yaklaşımıdır.

    3. Code First Yaklaşımı
       * Öncelikle veritabanına karşılık gelicek class'lar ve class özellikleri yazılır. Sonra veritabanı oluşturulur.

## HIBERNATE

- Hibernate bir java ORM framework'üdür.

   ### HIBERNATE MİMARİSİ

    * Hibernate mimarisi 4 katmandan oluşmaktadır. Uygulama Katmanı, Hibernate Çerçeve Katmanı, Backend API Katmanı, Veritabanı Katmanı.

    * Hibernate mimarisinin ögeleri:

        | Element             | Tanım                                                                                                                 |
        |---------------------|-----------------------------------------------------------------------------------------------------------------------|
        | **SessionFactory**  | Veritabanı işlemlerinin gerçekleştiren ana araçtır. SQL temelli sorgular oluşturmak, çalıştırma için kullanılır.      |
        | **Session**         | Varsayılan olmayan tüm bilgilerin saklandığı yerdir. Session oluşturmak için kullanılır.                              |
        | **Transaction**     | Veritabanında gerçekleştirilen persistence işlemlerini kalıcı hale gelmesini sağlar.                                  |

    ### HIBERNATE YAPILANDIRMA DOSYASI

    - Session factory'i başlatmak için kullanılır. Kullanılacak veri tabanı bilgilerini içerir. Hibernate'i yapılandırma için 2 yol bulunur.

        1. hibernate.cfg.xml dosyası ile configuration gerçekleştirme

            ```xml
            <hibernate-configuration>
                <session-factory>
                    <!-- SQL Dialect -->
                    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

                    <!-- Database Connection Settings -->
                    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
                    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/consumer</property>
                    <property name="hibernate.connection.username">username</property>
                    <property name="hibernate.connection.password">password</property>
                    <property name="show_sql">true</property>

                    <!-- Specifying Session Context -->
                    <property name="hibernate.current_session_context_class">org.hibernate.context.internal.ThreadLocalSessionContext</property>

                    <!-- Mapping With Model Class Containing Annotations -->
                    <mapping class="org.example.entities.Consumer" />
                </session-factory>
            </hibernate-configuration>  
            ```

	    2. Java temelli ek açıklamalar ile configuration gerçekleştire.

             ```code
            public class HibernateUtil {

                 private static SessionFactory sessionFactory;

                 public static SessionFactory getSessionFactory() {

                    if (sessionFactory == null) {

                        try {
                            Configuration configuration = new Configuration();

                            Properties settings = new Properties();
                            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/consumer");
                            settings.put(Environment.USER, "username");
                            settings.put(Environment.PASS, "userpassword");

                            settings.put(Environment.SHOW_SQL, "true");

                            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                            configuration.setProperties(settings);

                            configuration.addAnnotatedClass(Consumer.class);

                            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return sessionFactory;
                }
            }
            ```

    ### HIBERNATE ANOTASYONLARI

    | Anotasyon                                               | Tanım                                                                                                                 |
    |---------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
    | **@Entity**                                             | Tanımlanınan class'ı veritabanı varlığı olarak belirtmek için kullanılır.                                             |
    | **@Table(name = "table_name")**                         | Veritabanında class'ın hangi tablo'ya denk geldiğini belitmek için kullanırız.                                        |
    | **@Id**                                                 | Primary key'i belirtmek için kullanılır.                                                                              |
    | **@GeneratedValue(strategy = GenerationType.SEQUENCE)** | Değerleri otomatik olarak oluşturmak için kullanılır. (Mesela ID)                                                     |
    | **@Column(name = "column_name")**                       | Niteliğin hangi sütun'a denk geldiğini belirtmek için kullanılır.                                                     |
    | **@ManyToOne**                                          | Many to One ilişkilerde kullanılır. Örn: bir kişinin birden çok mesajı olabilir.                                      |
    | **@OneToMany**                                          | One to Many ilişkilerde kullanılır. Örn: bir kişinin birden çok görevi olabilir.                                      |
    | **@ManyToMany**                                         | Bir çalışan birden fazla projede çalışabilir, bir projede birden çok çalışan olabilir. Bu tarz ilişkilerde kullanılır.|
    | **@OneToOne**                                           | Bir arabanın bir adet sigortası vardır. Bir sigorta biligiside bir arabaya aittir. Bu tarz ilişkilerde kullanılır.    |

## GenerationType

| Anotasyon        | Tanım                                                                                                                 |
|------------------|-----------------------------------------------------------------------------------------------------------------------|
| **AUTO**         | JPA sağlayıcısı tarafından belirlenen bir yöntem kullanılarak birincil anahtar üretilir.                              |
| **IDENTITY**     | Veritabanı tarafından otomatik olarak birincil anahtar üretilir. Bu yöntemde, veritabanı tarafından otomatik olarak birincil anahtar üretilir. Bu yöntem, veritabanı bağımsızlığı sağlamaz ve birincil anahtarların nasıl oluşturulacağına karar verme esnekliği sağlamaz. Ayrıca, bu yöntemle birincil anahtarlar otomatik olarak artırılamaz.                                                     |
| **SEQUENCE**     | Veritabanı tarafından otomatik olarak birincil anahtar üretilir. Bu yöntemde, bir dizi kullanılarak birincil anahtarlar üretilir. Bu yöntem, veritabanı bağımsızlığı sağlar ve birincil anahtarların nasıl oluşturulacağına karar verme esnekliği sağlar. Ayrıca, bu yöntemle birincil anahtarlar otomatik olarak artırılabilir.                                                     |
| **TABLE**        | Bir tablo kullanılarak birincil anahtar üretilir.                                                                     |
