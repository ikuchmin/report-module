package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Try;
import ru.osslabs.Dispatcher;
import ru.osslabs.model.datasource.IData;
import ru.osslabs.modules.report.domain.Lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static javaslang.control.Option.of;
import static ru.osslabs.modules.report.reflections.ObjectUtils.cast;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ObjectRegistryImpl implements ObjectRegistry {
    private static Logger log = Logger.getLogger(ObjectRegistryImpl.class.getName());
    private Map<Class<?>, Function2<IData, ReferenceSupplier<?>, Try<?>>> factoryMethods;

    // TODO: May be you want make support function for Try.of -> filter -> map -> recover. More, more copy/paste
    public ObjectRegistryImpl() {
        factoryMethods = new HashMap<>();
        factoryMethods.put(Object.class, (obj, type) -> Try.of(() -> obj.getValue()) // Important: Don't replace on method reference. if obj is null, we get NPE ant then recover
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj.getValue() is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .map(d -> ((Dispatcher) d).dispatch(new ObjectFactoryImpl<>(this), type))
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" we have problem dispatching")
                        .concat(" or obj::getValue not was implemented Dispatcher.")
                        .concat(" Used default value.")
                        .concat(" Message: ").concat(e.getMessage())))
                .recover(e -> new NullableObject().dispatch(new ObjectFactoryImpl<>(this), type)));
        factoryMethods.put(Lookup.class, (obj, type) -> Try.of(() -> obj.getValueLabel()) // Important: Don't replace on method reference. if obj is null, we get NPE ant then recover
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj.getValue() is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .map(Lookup::new));
        factoryMethods.put(List.class, (obj, type) -> Try.of(() -> obj.getValue()) // Important: Don't replace on method reference. Ff obj is null, we get NPE ant then recover
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj is null. ")
                        .concat(" Message: ").concat(e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj.getValue() is null")
                        .concat(" Message: ").concat(e.getMessage())))
                .map(v -> ((Dispatcher) v).dispatch(new ListObjectFactory<>(this), cast(type)))
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" we have problem dispatching")
                        .concat(" or obj::getValue not was implemented Dispatcher.")
                        .concat(" Used default value.")
                        .concat(" Message: ").concat(e.getMessage())))
                .recover(e -> new NullableObject().dispatch(new ListObjectFactory<>(this), cast(type))));
        factoryMethods.put(Integer.class, (obj, type) -> Try.of(() -> obj.getValue()) // if obj is null, we get NPE ant then recover
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj.getValue() is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .map((v) -> Integer.valueOf(cast(v))));
        factoryMethods.put(String.class, (obj, type) -> Try.of(() -> obj.getValue()) // Important: Don't replace on method reference. if obj is null, we get NPE ant then recover
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj.getValue() is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .map(ObjectUtils::<String>cast));
        factoryMethods.put(Boolean.class, (obj, type) -> Try.of(() -> obj.getValue()) // Important: Don't replace on method reference. if obj is null, we get NPE ant then recover
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> "For type ".concat(type.getType().getTypeName()).concat(" obj.getValue() is null.")
                        .concat(" Message: ").concat(e.getMessage())))
                .map(v -> Boolean.valueOf(cast(v))));
    }

    /**
     * Method can return null if Object.class wasn't got registry.
     * if type don't find method should be used Object.class.
     *
     * @param <T>
     * @param cls
     * @return
     */
    @Override
    public <T> Function2<IData, ReferenceSupplier<? extends T>, Try<T>> dispatch(Class<?> cls) {
        return cast(of(factoryMethods.get(cls)).orElseGet(() -> factoryMethods.get(Object.class)));
    }
}
